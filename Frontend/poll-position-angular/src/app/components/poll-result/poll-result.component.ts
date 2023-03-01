import {Component, OnInit} from '@angular/core';
import {VoteResults} from "../../models/voteResults";
import {Poll} from "../../models/poll";
import {NominationsService} from "../../services/nominations.service";
import {Nomination} from "../../models/nomination";
import {ActivatedRoute} from "@angular/router";
import {VoteService} from "../../services/vote.service";
import {PollService} from "../../services/poll.service";

@Component({
  selector: 'app-poll-result',
  templateUrl: './poll-result.component.html',
  styleUrls: ['./poll-result.component.css']
})
export class PollResultComponent implements OnInit{
  voteResults: VoteResults[] = []
  nominations: Nomination[] = [];
  poll: Poll | undefined;
  constructor(
    private nominationService: NominationsService,
    private route: ActivatedRoute,
    private pollService: PollService,
    private voteService: VoteService
  ) {
  }

  getNominations():void {
    const pollID = this.route.snapshot.paramMap.get("pollID") as string;
    this.nominationService.getNominationsForPoll(pollID).subscribe(nominations => this.nominations = nominations);
  }

  getVoteResults():void {
    const pollID = this.route.snapshot.paramMap.get("pollID") as string;
    this.voteService.getPollResultsByPollID(pollID, this.nominations).subscribe(pollResults => this.voteResults = pollResults);
  }

  getPoll():void {
    const pollID = this.route.snapshot.paramMap.get("pollID") as string;
    this.pollService.getSpecificPoll(pollID).subscribe(poll => this.poll = poll);
  }

  ngOnInit(): void {
    this.getPoll();
    this.getNominations();
    this.getVoteResults();
  }
}
