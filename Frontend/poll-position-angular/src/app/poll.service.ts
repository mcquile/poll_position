import { Injectable } from '@angular/core';
import {Observable, of} from "rxjs";
import {Poll} from "./models/poll";
import {POLLS} from "./mock-polls";

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

  isCreatorEmailUserEmail(poll: Poll): boolean{
    return poll.pollCreator.email === localStorage.getItem("email");
  }
}
