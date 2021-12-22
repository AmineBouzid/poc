import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

interface Project {
  nom_projet: String;
  manager_id: Number;
}

@Component({
  selector: 'app-manager-app',
  templateUrl: './manager-app.component.html',
  styleUrls: ['./manager-app.component.css']
})
export class ManagerAppComponent implements OnInit {
  form: any = {
    nom_projet: null,
    manager_id: null
  };

  authorized = false;
  content?: string;
  errorMessage = '';
  isSuccessful = false;
  isSignUpFailed = false;
  currentUser: any;

  constructor(private token: TokenStorageService, private userService: UserService) { }

  ngOnInit(): void {
    this.currentUser = this.token.getUser();
    this.userService.getProjectBoard().subscribe(
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
    const { nom_projet } = this.form;
    this.userService.addProject(nom_projet, this.currentUser.id).subscribe(
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
