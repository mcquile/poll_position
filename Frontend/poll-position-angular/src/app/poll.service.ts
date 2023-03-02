import { Injectable } from '@angular/core';
import {Observable, of} from "rxjs";
import {Poll} from "./models/poll";
import {POLLS} from "./mock-polls";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class PollService {

  constructor(private http:HttpClient) { }

  getPolls(): Observable<Poll[]>{
    return this.http.get<Poll[]>("http://localhost:8080/api/v1/polls",{
      headers:{
        "Authorization": "Bearer " + (localStorage.getItem("userToken") || "")
      }
    })
  }

  createPoll(request: object): Observable<Poll>{
    return this.http.post<Poll>("http://localhost:8080/api/v1/polls",request,{
      headers:{
        "Authorization": "Bearer " + (localStorage.getItem("userToken") || "")
      }
    });
  }

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
