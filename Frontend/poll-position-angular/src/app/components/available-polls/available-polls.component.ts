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
  }

  ngOnInit(): void {
    this.getAvailablePolls()
  }
}
