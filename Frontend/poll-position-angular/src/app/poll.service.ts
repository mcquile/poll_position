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
}
