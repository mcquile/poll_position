import {Component, OnInit} from '@angular/core';
import {Nomination} from "../../models/nomination";
import {NominationsService} from "../../services/nominations.service";
import {ActivatedRoute} from "@angular/router";
import {Poll} from "../../models/poll";
import {PollService} from "../../services/poll.service";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-voting-page',
  templateUrl: './voting-page.component.html',
  styleUrls: ['./voting-page.component.css']
})
export class VotingPageComponent implements OnInit{
  votingForm: FormGroup;

  nominations: Nomination[] = [];
  poll: Poll | undefined;

  constructor(
    private nominationService: NominationsService,
    private pollService: PollService,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder) {
    this.votingForm = this.formBuilder.group({
      nominations: new FormControl('')
    })
  }

  getNominations():void {
    const pollID = this.route.snapshot.paramMap.get("pollID") as string;
    this.nominationService.getNominationsForPoll(pollID).subscribe(nominations => this.nominations = nominations)
  }

  getPoll():void {
    const pollID = this.route.snapshot.paramMap.get("pollID") as string;
    this.pollService.getSpecificPoll(pollID).subscribe(poll => this.poll = poll);
  }

  submitVotingForm():void{}

  ngOnInit(): void {
    this.getNominations();
    this.getPoll();
  }


}