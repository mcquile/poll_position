import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { UserCreatedPollsComponent } from './user-created-polls/user-created-polls.component';
import { AvailablePollsComponent } from './available-polls/available-polls.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    UserCreatedPollsComponent,
    AvailablePollsComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
