import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AppSettings} from "../../appSettings";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  constructor(private router: Router) {
  }

  logout() {
    localStorage.clear();
    this.router.navigateByUrl("/");
  }

  ngOnInit(): void {
    AppSettings.handleUserNotAuthenticated();
  }
}
