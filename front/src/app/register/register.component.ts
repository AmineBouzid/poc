import { nullSafeIsEquivalent } from '@angular/compiler/src/output/output_ast';
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import { UserService } from '../_services/user.service';

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


  constructor(private authService: AuthService, private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getAllManagers().subscribe(
      data => {
        this.managers = data;
      },
    )
  }

  onSubmit(): void {

    this.role.push("user");
    if (this.checkedManager) {
      this.role.push("manager");
    }
    if (this.checkedAdmin) {
      this.role.push("admin");
    }

    console.log(this.role)
    const { nom, prenom, username, email, password, manager } = this.form;

    if (manager == null) {
      this.managerNotAdded = true;
    }
    console.log(manager);
    this.authService.register(username, email, password, manager, nom, prenom, this.role).subscribe(
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
