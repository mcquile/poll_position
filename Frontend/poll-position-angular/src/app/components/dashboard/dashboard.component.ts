import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AppSettings} from "../../appSettings";
import {UserService} from "../../services/user.service";
import {User} from "../../models/user";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  constructor(private router: Router,
              private userService:UserService) {
  }

  logout() {
    localStorage.clear();
    this.router.navigateByUrl("/");
  }

  ngOnInit(): void {
    let currentUser:User = {
      firstName:"",
      lastName:"",
      email:""
    };
    this.userService.getUserByToken().subscribe(user => {
      currentUser = user;
      localStorage.setItem("currentUser", JSON.stringify(currentUser));
    });
    AppSettings.handleUserNotAuthenticated();
  }
}
