import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { SocialLoginModule, SocialAuthServiceConfig } from '@abacritt/angularx-social-login';
import { GoogleLoginProvider } from '@abacritt/angularx-social-login';

import { AppComponent } from './app.component';
import {AuthenticationComponent} from './components/authentication/authentication.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { UserCreatedPollsComponent } from './components/user-created-polls/user-created-polls.component';
import { AvailablePollsComponent } from './components/available-polls/available-polls.component';
import { CreatePollComponent } from './components/create-poll/create-poll.component';
import {ReactiveFormsModule,FormsModule} from "@angular/forms";
import { CookieService } from 'ngx-cookie-service';

import { AppRoutingModule } from './app-routing.module';
import { VotingPageComponent } from './components/voting-page/voting-page.component';
import { PollResultComponent } from './components/poll-result/poll-result.component';

@NgModule({
  declarations: [
    AuthenticationComponent,
    AppComponent,
    DashboardComponent,
    UserCreatedPollsComponent,
    AvailablePollsComponent,
    CreatePollComponent,
    VotingPageComponent,
    PollResultComponent
  ],
    imports: [
      AppRoutingModule,
      FormsModule,
      BrowserModule,
      SocialLoginModule,
        ReactiveFormsModule
    ],
  providers:  [
    CookieService,
    {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider(
              '319344215899-3dn70bn4etaq4c9afcck0b5ajnattibn.apps.googleusercontent.com'
            )
          }
        ],
        onError: (err) => {
          console.error(err);
        }
      } as SocialAuthServiceConfig,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
