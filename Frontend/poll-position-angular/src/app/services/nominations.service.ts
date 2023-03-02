import { Injectable } from '@angular/core';
import {Observable, of} from "rxjs";
import {Nomination} from "../models/nomination";
import {NOMINATIONS} from "../mock-data/mock-nominations";

@Injectable({
  providedIn: 'root'
})
export class NominationsService {

  constructor() { }

  getNominationsForPoll(pollID: string): Observable<Nomination[]>{
    return of(NOMINATIONS.filter(nomination => nomination.poll.pollID === pollID))
  }
}