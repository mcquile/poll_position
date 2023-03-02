import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { CreatePollComponent } from './create-poll/create-poll.component';
import { AuthenticationComponent } from './authentication/authentication.component';
import {ViewPollComponent} from "./view-poll/view-poll.component";

const routes: Routes = [
  { path: '', component: AuthenticationComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'create-poll', component: CreatePollComponent },
  { path: 'view-poll/:id', component: ViewPollComponent },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
