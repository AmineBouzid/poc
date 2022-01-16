import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';
import {Project} from '../interfaces'


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

  form_delete: any = {
    project_id: null,
  }

  authorized = false;
  content?: string;
  errorMessage = '';
  isSuccessful = false;
  isSignUpFailed = false;
  currentUser: any;
  projects: Project[] = [];
  isDeleted = false;
  isDeletedFailed = false
  MessageDelete: any;
  id_to_delete: any;
  hideSuccessMessage = true;


  constructor(private cd: ChangeDetectorRef, private token: TokenStorageService, private userService: UserService) { }

  refresh() {
    this.cd.detectChanges();
  }
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
    this.userService.getAllProjects().subscribe(
      data => {
        this.projects = data;
      },
    );
  }


  onSubmit(): void {
    const { nom_projet } = this.form;
    this.userService.addProject(nom_projet, this.currentUser.id).subscribe(
      data => {
        console.log(data);
        this.isSuccessful = true;
        setTimeout(() => {
          this.hideSuccessMessage = false;
        }, 3000);
        this.isSignUpFailed = false;
        this.refresh();
        this.userService.getAllProjects().subscribe(
          data => {
            this.projects = data;
          },
        );

      },
      err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
        setTimeout(() => {
          this.hideSuccessMessage = true;
        }, 3000);
      }
    );
  }
  onDelete(): void {
    this.userService.deleteProject(this.id_to_delete).subscribe(
      data => {
        console.log(data);
        //this.MessageDelete = data.message;
        this.isDeleted = true;
        this.isDeletedFailed = false;
        setTimeout(() => {
          this.hideSuccessMessage = false;
        }, 4000);
        this.refresh();
        this.userService.getAllProjects().subscribe(
          data => {
            this.projects = data;
          },
        );
      },
      err => {
        setTimeout(() => {
          this.hideSuccessMessage = false;
        }, 4000);
        this.MessageDelete = err.message;
        this.isDeletedFailed = true;
      }
    )

  }

  FadeOutSuccessMsg() {

  }

}


