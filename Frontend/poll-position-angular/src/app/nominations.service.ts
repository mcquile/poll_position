import { Injectable } from '@angular/core';
import {Observable, of} from "rxjs";
import {Nomination} from "./models/nomination";

@Injectable({
  providedIn: 'root'
})
export class NominationsService {

  constructor() { }

  getNominationsForPoll(pollID: string): Observable<Nomination[]>{
    return of([{
      nominationID: 1,
    nominee: "Nominee 1",
    votes: [1,2,3],
  },{
      nominationID: 1,
      nominee: "Nominee 1",
      votes: [4,5],
    }
    ])
  }
}
