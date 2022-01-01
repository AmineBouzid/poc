import { nullSafeIsEquivalent } from '@angular/compiler/src/output/output_ast';
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import { UserService } from '../_services/user.service';
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
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form: any = {
    nom: null,
    username: null,
    email: null,
    password: null,
    manager: null,
    prenom: null,
    roles: null
  };
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';
  managerNotAdded = false;
  managers: User[] = [];
  selectedManager: string = "";
  role: string[] = [];
  checkedManager = false;
  checkedAdmin = false;
  authorized = false;
  usedByAdmin = false;
  content?: string;
  currentUser: any;

  constructor(private token: TokenStorageService, private authService: AuthService, private userService: UserService) { }

  ngOnInit(): void {
    this.currentUser = this.token.getUser();
    for (let item of this.currentUser.roles) {
      if (item == "ROLE_ADMIN") {
        this.usedByAdmin = true;
      }


    }

    this.userService.getAllManagers().subscribe(
      data => {
        this.managers = data;
      },
    )
    this.userService.getRegisterBoard().subscribe(
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
      console.log(item)
      if (item == "ROLE_ADMIN") {
        this.usedByAdmin = true;
      } 
    }

    if(!this.usedByAdmin){
      this.form.manager = this.currentUser.username;
    }

    this.authService.register(username, email, password, this.form.manager, nom, prenom, this.role).subscribe(
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
