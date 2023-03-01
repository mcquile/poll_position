import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { CreatePollComponent } from './create-poll/create-poll.component';
import { AuthenticationComponent } from './authentication/authentication.component';

const routes: Routes = [
  { path: '', component: AuthenticationComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'create-poll', component: CreatePollComponent },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
