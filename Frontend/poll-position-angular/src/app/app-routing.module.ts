import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DashboardComponent} from './components/dashboard/dashboard.component';
import {CreatePollComponent} from './components/create-poll/create-poll.component';
import {VotingPageComponent} from "./components/voting-page/voting-page.component";
import {PollResultComponent} from "./components/poll-result/poll-result.component";
import {ViewPollComponent} from "./view-poll/view-poll.component";
import {AuthenticationComponent} from "./components/authentication/authentication.component";

const routes: Routes = [
  {path: '', component: AuthenticationComponent},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'create-poll', component: CreatePollComponent},
  {path: 'vote/:pollID', component: VotingPageComponent},
  {path: 'results/:pollID', component: PollResultComponent},
  {path: 'view-poll/:id', component: ViewPollComponent},
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
