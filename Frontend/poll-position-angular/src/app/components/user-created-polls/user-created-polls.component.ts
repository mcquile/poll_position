import {Component, OnInit} from '@angular/core';
import {Poll} from "../../models/poll";
import {PollService} from "../../services/poll.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user-created-polls',
  templateUrl: './user-created-polls.component.html',
  styleUrls: ['./user-created-polls.component.css']
})
export class UserCreatedPollsComponent implements OnInit{
  polls: Poll[] = [];
  constructor(private pollService: PollService,
              private router: Router,
              ) {}

  ngOnInit(): void {
    this.getUserCreatedPolls();
  }

  getUserCreatedPolls(): void {
    this.pollService.getPolls().subscribe(polls => this.polls = polls.filter(poll => this.pollService.isCreatorEmailUserEmail(poll)));
  }

  navigateToCreatePoll():void {
      this.router.navigateByUrl('create-poll');
  }

  navigateToSpecificPoll(poll: Poll): void{
    this.router.navigateByUrl(`view-poll/${poll.pollID}`)
  }
}
