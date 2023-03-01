import { Injectable } from '@angular/core';
import {Observable, of} from "rxjs";
import {Nomination} from "../models/nomination";

@Injectable({
  providedIn: 'root'
})
export class NominationsService {

  constructor() { }

  getNominationsForPoll(pollID: string): Observable<Nomination[]>{
    return of()
  }
}
