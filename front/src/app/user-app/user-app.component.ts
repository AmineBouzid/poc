import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from '../_services/user.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ThemePalette } from '@angular/material/core';
import { AuthService } from '../_services/auth.service';
import { TokenStorageService } from '../_services/token-storage.service';
import autoTable from 'jspdf-autotable'
import jsPDF from 'jspdf'
import * as moment from 'moment';
import {User,Project,Time} from '../interfaces'

//declare var jsPDF: any;
@Component({
  selector: 'app-user-app',
  templateUrl: './user-app.component.html',
  styleUrls: ['./user-app.component.css']
})
export class UserAppComponent implements OnInit {
  form1 = new FormGroup({
    fromDate: new FormControl(null, { validators: [Validators.required] }),
    toDate: new FormControl(null, { validators: [Validators.required] })
  });

  form_others = new FormGroup({
    fromDate: new FormControl(null, { validators: [Validators.required] }),
    toDate: new FormControl(null, { validators: [Validators.required] })
  });

  form_user: any = {
    username_options: null,
    username_id: null,
  }

  form: any = {
    nom_project: null,
    id_project: null,
  };

  date = new FormControl();
  content?: string;

  @ViewChild('picker') picker: any;

  errorMessage = '';
  disabled = false;
  showSpinners = true;
  showSeconds = false;
  touchUi = false;
  enableMeridian = false;
  stepHour = 1;
  stepMinute = 1;
  stepSecond = 1;
  color: ThemePalette = 'primary';
  disableMinute = false;
  hideTime = false;
  projects: Project[] = [];
  isSuccessful = false;
  isSignUpFailed = false;
  currentUser: any;
  authorized = false;
  times: Time[] = [];
  times_other: Time[] = [];
  displayedColumns: any = [];
  users: User[] = [];
  usedByAdminOrManager = false;
  var: Time | undefined;
  minDate!: Date;
  maxDate!: Date;
  times2: Time[] = [];
  hideSuccessMessage = true;



  public dateControl = new FormControl(null);

  closePicker() {
    this.picker.cancel();
  }

  constructor(private cd: ChangeDetectorRef, private token: TokenStorageService, private authService: AuthService, private userService: UserService) { }

  refresh() {
    this.cd.detectChanges();
  }

  ngOnInit(): void {

    var date = new Date();
    this.minDate = new Date(date.getFullYear(), date.getMonth(), 1);
    this.maxDate = new Date(date.getFullYear(), date.getMonth() + 1, 1);

    this.currentUser = this.token.getUser();
    for (let item of this.currentUser.roles) {
      if (item == "ROLE_ADMIN" || item == "ROLE_MANAGER") {
        this.usedByAdminOrManager = true;
      }
    }

    this.userService.getUserTimes(this.currentUser.id).subscribe(
      data => {
        this.times = data;

        this.displayedColumns = Object.keys(data[0])
        this.displayedColumns.unshift("checked");
        console.log(this.minDate);
        console.log(this.maxDate);
        for (let item in this.times) {
          this.times[item].date_travail = new Date(this.times[item].date_travail);
          console.log(this.times[item].date_travail);
        }
        this.times = this.times.filter(e =>
          (e.date_travail >= this.minDate && e.date_travail <= this.maxDate));
      }
    )
    this.userService.getUserBoard().subscribe(
      data => {
        this.content = data;
        this.authorized = true;
      },
      err => {
        this.content = JSON.parse(err.error).message;
        this.authorized = false;
      }
    );
    this.userService.getAllProjects().subscribe(
      data => {
        this.projects = data;
      },
    )
    this.userService.getAllUsers().subscribe(
      data => {
        this.users = data;
      },
    )
  }

  download(): void {
    const doc = new jsPDF()
    autoTable(doc, { html: '#myTable', didParseCell: data => { //hide the first column
      if (data.column.index === 0) {
        data.cell.styles.cellWidth = 0.1,
        data.cell.styles.overflow = 'hidden'
      }
    } })
    doc.save('test.pdf');
  }
  downloadOthers(): void {
    const doc1 = new jsPDF()
    autoTable(doc1, { html: '#otherTable' ,didParseCell: data => {
      if (data.column.index === 0) {
        data.cell.styles.cellWidth = 0.1,
        data.cell.styles.overflow = 'hidden'
      }}})
    doc1.save('test_other.pdf');
  }

  onSubmit(): void {

    var date_saisie = new Date().toLocaleString();

    console.log(new Date().toLocaleString());
    console.log(this.form.id_project);
    console.log(this.dateControl.value?.toLocaleTimeString([], {
      year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit',
      minute: '2-digit'
    }))


    var nb_hours = this.dateControl.value?.toLocaleTimeString([], {
      year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit',
      minute: '2-digit'
    })

    this.userService.addTime(date_saisie, nb_hours, this.currentUser.id, this.form.id_project).subscribe(
      data => {
        console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
        this.refresh();
        setTimeout(() => {
          this.hideSuccessMessage = false;
        }, 3000);
        this.reload();
      },
      err => {
        this.errorMessage = err.error.message;
        console.log(this.errorMessage);
        this.isSignUpFailed = true;
      }
    );


  }

  onChosenUser(): void {
    this.userService.getUserTimes(this.form_user.username_id).subscribe(
      data => {
        this.times_other = data;
        this.displayedColumns = Object.keys(data[0])
        this.displayedColumns.unshift("checked");
        for (let item in this.times) {
          this.times[item].date_travail = new Date(this.times[item].date_travail);
          console.log(this.times[item].date_travail);
        }
        this.times = this.times.filter(e =>
          (e.date_travail >= this.minDate && e.date_travail <= this.maxDate));
      }
    )
  }

  getTotalCost() {
    let nb_hours_list = [];
    for (let item in this.times) {
      nb_hours_list.push(<string>this.times[item].nb_hours);
    }
    const sum = nb_hours_list.reduce((acc, time) => acc.add(moment.duration(time)), moment.duration());
    //console.log([Math.floor(sum.asHours()), sum.minutes()].join(':'));
    return [Math.floor(sum.asHours()), sum.minutes()].join(':');
  }

  getTotalCostOthers() {
    let nb_hours_list = [];
    for (let item in this.times_other) {
      nb_hours_list.push(<string>this.times_other[item].nb_hours);
    }

    const sum = nb_hours_list.reduce((acc, time) => acc.add(moment.duration(time)), moment.duration());

    //console.log([Math.floor(sum.asHours()), sum.minutes()].join(':'));
    return [Math.floor(sum.asHours()), sum.minutes()].join(':');
  }


  applyDateFilter() {
    this.userService.getUserTimes(this.currentUser.id).subscribe(
      data => {
        this.times = data;
        this.displayedColumns = Object.keys(data[0])
        this.displayedColumns.unshift("checked");
        for (let item in this.times) {
          this.times[item].date_travail = new Date(this.times[item].date_travail);
          console.log(this.times[item].date_travail);
        }
        this.times = this.times.filter(e =>
          (e.date_travail >= this.form1.value.fromDate && e.date_travail <= this.form1.value.toDate));
      }
    )
    if (this.form1.invalid) {
      return
    }

    //this.dataSource.data = this.dataSource.data.filter(e=>e.date >= this.fromDate && e.date <= this.toDate);
  }

  resetFilter() {
    this.reload()
  }

  applyDateFilterOthers() {
    this.userService.getUserTimes(this.form_user.username_id).subscribe(
      data => {
        this.times_other = data;
        this.displayedColumns = Object.keys(data[0])
        this.displayedColumns.unshift("checked");
        for (let item in this.times_other) {
          this.times_other[item].date_travail = new Date(this.times_other[item].date_travail);
          console.log(this.times_other[item].date_travail);
        }
        this.times_other = this.times_other.filter(e =>
          (e.date_travail >= this.form_others.value.fromDate && e.date_travail <= this.form_others.value.toDate));
      }
    )
    if (this.form_others.invalid) {
      return
    }

    //this.dataSource.data = this.dataSource.data.filter(e=>e.date >= this.fromDate && e.date <= this.toDate);
  }

  resetFilterOthers() {
    this.reloadOthers()
  }
  deleteTime(element: any) {
    console.log(element.id_time);
    this.userService.deleteTime(element.id_time).subscribe(
      data => {
        this.reload()
        console.log(data);
      },
      err => {
        console.log(err);
      }
    )
  }

  deleteTimeOthers(element: any) {
    console.log(element.id_time);
    this.userService.deleteTime(element.id_time).subscribe(
      data => {
        this.reloadOthers()
        console.log(data);
      },
      err => {
        console.log(err);
      }
    )
  }



  reload() {
    this.userService.getUserTimes(this.currentUser.id).subscribe(
      data => {
        this.times = data;

        this.displayedColumns = Object.keys(data[0])
        this.displayedColumns.unshift("checked");
        console.log(this.minDate);
        console.log(this.maxDate);
        for (let item in this.times) {
          this.times[item].date_travail = new Date(this.times[item].date_travail);
          console.log(this.times[item].date_travail);
        }
        this.times = this.times.filter(e =>
          (e.date_travail >= this.minDate && e.date_travail <= this.maxDate));
      }
    )
  }

  reloadOthers() {
    this.userService.getUserTimes(this.form_user.username_id).subscribe(
      data => {
        this.times_other = data;
        this.displayedColumns = Object.keys(data[0])
        this.displayedColumns.unshift("checked");
        for (let item in this.times) {
          this.times[item].date_travail = new Date(this.times[item].date_travail);
          console.log(this.times[item].date_travail);
        }
        this.times = this.times.filter(e =>
          (e.date_travail >= this.minDate && e.date_travail <= this.maxDate));
      }
    )
  }


}
