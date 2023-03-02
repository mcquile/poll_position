import {Injectable} from '@angular/core';
import {UserVote} from "../models/userVote";
import {Nomination} from "../models/nomination";

@Injectable({
  providedIn: 'root'
})
export class VoteService {
  userVotes: UserVote[] = [];

  constructor() {
  }

  getVotesByPollID(pollID: string)/*: Observable<UserVote[]> */ {
    // return of(USERVOTES.filter(userVote => userVote.nomination.poll.id === pollID));
  }

  getPollResultsByPollID(pollID: string, nominations: Nomination[])/*: Observable<VoteResults[]> */ {
    // this.getVotesByPollID(pollID).subscribe(userVotes => this.userVotes = userVotes);
    // let voteResults: VoteResults[] = [];
    // nominations.forEach(nomination => {
    //   let count: number = 0;
    //   this.userVotes.forEach(userVote => {
    //     if (userVote.nomination === nomination) {
    //       count++;
    //     }
    //   })
    //   voteResults.push({
    //     nomination: nomination,
    //     totalVotes: count
    //   })
    // })
    // return of(voteResults);
  }
}
