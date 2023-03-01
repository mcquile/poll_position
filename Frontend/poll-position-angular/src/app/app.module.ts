import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { UserCreatedPollsComponent } from './user-created-polls/user-created-polls.component';
import { AvailablePollsComponent } from './available-polls/available-polls.component';
import { CreatePollComponent } from './create-poll/create-poll.component';
import {ReactiveFormsModule} from "@angular/forms";
import { AppRoutingModule } from './app-routing.module';
import { VotingPageComponent } from './voting-page/voting-page.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    UserCreatedPollsComponent,
    AvailablePollsComponent,
    CreatePollComponent,
    VotingPageComponent
  ],
    imports: [
        BrowserModule,
        ReactiveFormsModule,
        AppRoutingModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
