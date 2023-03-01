import {Component, OnInit} from '@angular/core';
import {Poll} from "../../models/poll";
import {PollService} from "../../services/poll.service";

@Component({
  selector: 'app-available-polls',
  templateUrl: './available-polls.component.html',
  styleUrls: ['./available-polls.component.css']
})
export class AvailablePollsComponent implements OnInit{
  polls: Poll[] = [];

  constructor(private pollService: PollService) {}

  getAvailablePolls(): void {
    this.pollService.getAllPolls().subscribe(polls => this.polls = polls.filter(poll => !this.pollService.isCreatorEmailUserEmail(poll)));
  }

  ngOnInit(): void {
    this.getAvailablePolls()
  }
}
