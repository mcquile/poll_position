import {Component, OnInit} from '@angular/core';
import {PollService} from "../../services/poll.service";
import {Poll} from "../../models/poll";

@Component({
  selector: 'app-completed-polls',
  templateUrl: './completed-polls.component.html',
  styleUrls: ['./completed-polls.component.css']
})
export class CompletedPollsComponent implements OnInit {
  polls: Poll[] = [];

  constructor(private pollService: PollService) {
  }

  getCompletedPolls(): void {
  }

  ngOnInit(): void {
    this.getCompletedPolls()
  }

}
