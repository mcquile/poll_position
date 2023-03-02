import {Component, OnInit} from '@angular/core';
import {SocialAuthService, SocialUser} from '@abacritt/angularx-social-login';
import {AppSettings} from '../../appSettings';
import axios from 'axios';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.css'],
})
export class AuthenticationComponent implements OnInit {
  constructor(private authService: SocialAuthService) {
  }

  ngOnInit(): void {
    AppSettings.handleUserAuthenticated('/dashboard/');

    this.authService.authState.subscribe((user: SocialUser) => {
      this.handleCreateSocialUser(user);
    });
  }

  public handleUserLogin(loginForm: NgForm): void {
    const endpoint: string = AppSettings.API_ENDPOINT.concat('/users/login/');
    let data = loginForm.value;

    axios.post(endpoint, data).then((response) => {
      localStorage.setItem('userToken', response.data.token);
      window.location.href = '/';
    });
  }

  public handleUserRegistration(regForm: NgForm): void {
    const endpoint: string =
      AppSettings.API_ENDPOINT.concat('/users/register/');
    let data = {
      firstname: regForm.value.firstname,
      lastname: regForm.value.lastname,
      email: regForm.value.reg_email,
      password: regForm.value.reg_password,
    };
    axios.post(endpoint, data).then((response) => {
      localStorage.setItem('userToken', response.data.token);
      window.location.href = '/';
    });
  }

  private handleCreateSocialUser(user: SocialUser): void {
    const endpoint: string = AppSettings.API_ENDPOINT.concat(
      '/users/register/oauth2/'
    );

    let data = {
      emailAddress: user.email,
      firstname: user.firstName,
      lastname: user.lastName,
      token: user.idToken,
      profilePic: user.photoUrl,
    };

    axios.post(endpoint, data).then((response) => {
      localStorage.setItem('userToken', response.data.token);
      window.location.href = '/';
    });
  }
}
