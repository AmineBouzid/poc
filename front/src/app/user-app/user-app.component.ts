import { Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from '../_services/user.service';
import { FormControl } from '@angular/forms';
import { ThemePalette } from '@angular/material/core';
import { AuthService } from '../_services/auth.service';
import { TokenStorageService } from '../_services/token-storage.service';


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


@Component({
  selector: 'app-user-app',
  templateUrl: './user-app.component.html',
  styleUrls: ['./user-app.component.css']
})
export class UserAppComponent implements OnInit {

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



  public dateControl = new FormControl(null);


  closePicker() {
    this.picker.cancel();
  }

  constructor(private token: TokenStorageService, private authService: AuthService, private userService: UserService) { }

  ngOnInit(): void {
    this.currentUser = this.token.getUser();
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
}
