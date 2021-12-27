import { Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from '../_services/user.service';
import { FormControl } from '@angular/forms';
import { ThemePalette } from '@angular/material/core';
import { AuthService } from '../_services/auth.service';
import { TokenStorageService } from '../_services/token-storage.service';
import autoTable from 'jspdf-autotable'
import jsPDF from 'jspdf'
import * as moment from 'moment';

interface User {
  id: Number;
  username: String;
  email: String;
  password: String;
  roles: [];
  nom: String;
  prenom: String;
  manager: any;
}

interface Project {
  id_project: Number;
  project_name: String;
  manager: User;
}

interface Time {
  id_time: Number,
  date_saisie: Date,
  date_travail: Date,
  nb_hours: String,
  user: User,
  project: Project,
}

//declare var jsPDF: any;
@Component({
  selector: 'app-user-app',
  templateUrl: './user-app.component.html',
  styleUrls: ['./user-app.component.css']
})
export class UserAppComponent implements OnInit {
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
  displayedColumns!: string[];
  users: User[] = [];
  usedByAdminOrManager = false;
  var: Time | undefined;




  public dateControl = new FormControl(null);


  closePicker() {
    this.picker.cancel();
  }

  constructor(private token: TokenStorageService, private authService: AuthService, private userService: UserService) { }


  ngOnInit(): void {
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
    autoTable(doc, { html: '#myTable' })
    doc.save('test.pdf');
  }
  downloadOthers(): void {
    const doc1 = new jsPDF()
    autoTable(doc1, { html: '#otherTable' })
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
      },
      err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    );
  }

  onChosenUser(): void {
    this.userService.getUserTimes(this.form_user.username_id).subscribe(
      data => {
        this.times_other = data;
        this.displayedColumns = Object.keys(data[0])
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

}
