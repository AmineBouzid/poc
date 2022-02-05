import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';
import {User} from '../interfaces'

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  currentUser: any;
  user!: User;
  content: any;
  authorized = false;

  constructor(private token: TokenStorageService, private userService: UserService) { }

  ngOnInit(): void {
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
    this.currentUser = this.token.getUser();
    this.userService.getUserById(this.currentUser.id).subscribe(
      data => {
        this.user = data;
      }
    )
    //console.log(this.user.manager)
  }

}
