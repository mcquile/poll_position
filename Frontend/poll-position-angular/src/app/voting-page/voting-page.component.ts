import {Component, OnInit} from '@angular/core';
import {Nomination} from "../models/nomination";
import {NominationsService} from "../nominations.service";
import {ActivatedRoute} from "@angular/router";
import {Poll} from "../models/poll";
import {PollService} from "../poll.service";

@Component({
  selector: 'app-voting-page',
  templateUrl: './voting-page.component.html',
  styleUrls: ['./voting-page.component.css']
})
export class VotingPageComponent implements OnInit{
  nominations: Nomination[] = [];
  poll: Poll | undefined;

  constructor(
    private nominationService: NominationsService,
    private pollService: PollService,
    private route: ActivatedRoute,) {
  }

  getNominations():void {
    const pollID = this.route.snapshot.paramMap.get("pollID") as string;
    this.nominationService.getNominationsForPoll(pollID).subscribe(nominations => this.nominations = nominations)
  }

  getPoll():void {
    const pollID = this.route.snapshot.paramMap.get("pollID") as string;
    this.pollService.getSpecificPoll(pollID).subscribe(poll => this.poll = poll);
  }

  ngOnInit(): void {
    this.getNominations();
    this.getPoll();
  }


}
