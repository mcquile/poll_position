import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { CreatePollComponent } from './components/create-poll/create-poll.component';
import { AuthenticationComponent } from './components/authentication/authentication.component';
import {VotingPageComponent} from "./components/voting-page/voting-page.component";

const routes: Routes = [
  { path: '', component: AuthenticationComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'create-poll', component: CreatePollComponent },
  { path: 'vote/:pollID', component: VotingPageComponent }
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
