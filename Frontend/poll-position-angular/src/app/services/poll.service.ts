import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Poll} from "../models/poll";

@Injectable({
  providedIn: 'root'
})
export class PollService {

  constructor(private http: HttpClient) {
  }

  getPolls(): Observable<Poll[]> {
    return this.http.get<Poll[]>("http://localhost:8080/api/v1/polls", {
      headers: {
        "Authorization": "Bearer " + (localStorage.getItem("userToken") || "")
      }
    })
  }

  createPoll(request: object): Observable<Poll> {
    return this.http.post<Poll>("http://localhost:8080/api/v1/polls", request, {
      headers: {
        "Authorization": "Bearer " + (localStorage.getItem("userToken") || "")
      }
    });
  }

  getPollByID(pollID:string): Observable<Poll>{
    return this.http.get<Poll>(`http://localhost:8080/api/v1/polls/${pollID}`, {
      headers: {
        "Authorization": "Bearer " + (localStorage.getItem("userToken") || "")
      }
    })
  }


  isCreatorEmailUserEmail(poll: Poll): boolean {
    let a = JSON.parse(localStorage.getItem("currentUser") || "");
    return poll.creator.email === a.email;
  }
}
