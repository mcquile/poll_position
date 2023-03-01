import { Injectable } from '@angular/core';
import {Observable, of} from "rxjs";
import {UserVote} from "../models/userVote";
import {USERVOTES} from "../mock-data/mock-userVotes";
import {VoteResults} from "../models/voteResults";
import {Nomination} from "../models/nomination";

@Injectable({
  providedIn: 'root'
})
export class VoteService {
  userVotes: UserVote[] = [];
  constructor() { }

  getVotesByPollID(pollID: string): Observable<UserVote[]>{
    return of(USERVOTES.filter(userVote => userVote.nomination.poll.pollID === pollID));
  }

  getPollResultsByPollID(pollID: string, nominations: Nomination[]): VoteResults[]{
    this.getVotesByPollID(pollID).subscribe(userVotes => this.userVotes = userVotes);
    let voteResults: VoteResults[] = [];
    nominations.forEach( nomination => {
      let count:number = 0;
      this.userVotes.forEach(userVote => {
        if (userVote.nomination === nomination) {
          count++;
        }
      })
      voteResults.push({
        nomination: nomination,
        totalVotes: count
      })
    })
    return voteResults;
  }
}
