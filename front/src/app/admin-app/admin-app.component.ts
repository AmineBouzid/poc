import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';

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

interface Roles {
  id: number;
  name: string;
}

@Component({
  selector: 'app-admin-app',
  templateUrl: './admin-app.component.html',
  styleUrls: ['./admin-app.component.css']
})

export class AdminAppComponent implements OnInit {

  form_user: any = {
    username_options: null,
    username_id: null,
  }

  form: any = {
    id: null,
    nom: null,
    username: null,
    email: null,
    password: null,
    manager: null,
    prenom: null,
    roles: null
  };
  isSuccessful = false;
  isUpdateFailed = false;
  errorMessage = '';
  managerNotAdded = false;
  managers: User[] = [];
  users: User[] = [];
  selectedManager: string = "";
  role: string[] = [];
  checkedManager = false;
  checkedAdmin = false;
  authorized = false;
  usedByAdmin = false;
  content?: string;
  currentUser: any;

  constructor(private snackBar: MatSnackBar, private token: TokenStorageService, private authService: AuthService, private userService: UserService) { }

  ngOnInit(): void {
    this.currentUser = this.token.getUser();
    for (let item of this.currentUser.roles) {
      if (item == "ROLE_ADMIN") {
        this.usedByAdmin = true;
      }


    }
    this.userService.getAllUsers().subscribe(
      data => {
        this.users = data;
      },
    )
    this.userService.getAllManagers().subscribe(
      data => {
        this.managers = data;
      },
    )
    this.userService.getAdminBoard().subscribe(
      data => {
        this.content = data;
        this.authorized = true;
      },
      err => {
        this.content = JSON.parse(err.error).message;
        this.authorized = false;
      }
    );

  }


  onChosenUser(): void {
    this.snackBar.open('Be Careful, Password and Manager are not loaded', 'Close');
    console.log("you chose:" + this.form_user.username_id)
    this.userService.getUserById(this.form_user.username_id).subscribe(
      data => {
        this.form = data;
        this.form.password = "";
        for (let item of this.form.roles) {
          if (item.name == "ROLE_ADMIN") {
            this.checkedAdmin = true;
            this.checkedManager = true;
          } else {
            if (item.name == "ROLE_MANAGER") {
              this.checkedManager = true;
            }
          }
        }
      }
    )
  }


  onSubmit(): void {

    this.role.push("user");
    if (this.checkedManager) {
      this.role.push("manager");
    }
    if (this.checkedAdmin) {
      this.role.push("admin");
      this.role.push("manager");
    }

    console.log(this.role)
    const { nom, prenom, username, email, password, manager } = this.form;

    if (manager == null) {
      this.managerNotAdded = true;
    }

    for (let item of this.currentUser.roles) {
      if (item == "ROLE_ADMIN") {
        this.usedByAdmin = true;
      } else {
        this.form.manager = this.currentUser.username;
      }
    }

    this.authService.updateUser(this.form_user.username_id, this.form.username, this.form.email, this.form.password, this.form.manager, this.form.nom, this.form.prenom, this.role).subscribe(
      data => {
        console.log(data);
        this.isSuccessful = true;
        this.isUpdateFailed = false;
      },
      err => {
        this.errorMessage = err.error.message;
        this.isUpdateFailed = true;
      }
    );
  }

}
