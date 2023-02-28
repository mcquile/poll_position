import {Component, OnInit} from '@angular/core';
import {Poll} from "../models/poll";
import {PollService} from "../poll.service";

@Component({
  selector: 'app-user-created-polls',
  templateUrl: './user-created-polls.component.html',
  styleUrls: ['./user-created-polls.component.css']
})
export class UserCreatedPollsComponent implements OnInit{
  polls: Poll[] = [];

  constructor(private pollService: PollService) {}

  getUserCreatedPolls(): void {
    this.pollService.getUserCreatedPolls().subscribe(polls => this.polls = polls);
  }

  ngOnInit(): void {
    this.getUserCreatedPolls();
  }
}
