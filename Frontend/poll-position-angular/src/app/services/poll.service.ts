import { Injectable } from '@angular/core';
import {Observable, of} from "rxjs";
import {Poll} from "../models/poll";
import {POLLS} from "../mock-data/mock-polls";

@Injectable({
  providedIn: 'root'
})
export class PollService {

  constructor() { }

  getUserCreatedPolls(): Observable<Poll[]> {
    return of(POLLS);
  }

  getAvailablePolls(): Observable<Poll[]> {
    return of(POLLS);
  }

  getAllPolls(): Observable<Poll[]> {
    return of(POLLS);
  }

  getSpecificPoll(pollID: string): Observable<Poll>{
    return of(POLLS.filter(poll => poll.pollID === pollID)[0])
  }

  isCreatorEmailUserEmail(poll: Poll): boolean{
    return poll.pollCreator.email === localStorage.getItem("email");
  }
}
