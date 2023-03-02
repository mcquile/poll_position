import {Injectable} from '@angular/core';
import {UserVote} from "../models/userVote";
import {Nomination} from "../models/nomination";
import {Poll} from "../models/poll";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class VoteService {
  userVotes: UserVote[] = [];

  constructor(private http: HttpClient) {
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

  voteForPoll(poll: Poll,nomination: Nomination){
    console.log(nomination);
    return this.http.post(`http://localhost:8080/api/v1/polls/${poll.id}/nominations/${nomination.id}/vote`,{}, {
      headers: {
        "Authorization": "Bearer " + (localStorage.getItem("userToken") || "")
      }
    }).subscribe(complete => console.log(complete));
  }
}
