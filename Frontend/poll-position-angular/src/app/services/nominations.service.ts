import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Nomination} from "../models/nomination";
import {Observable} from "rxjs";

// import {NOMINATIONS} from "../mock-data/mock-nominations";

@Injectable({
  providedIn: 'root'
})
export class NominationsService {

  constructor(private http: HttpClient) {
  }

  getNominationsForPoll(pollID: string): Observable<Nomination[]> {
    return this.http.get<Nomination[]>(`http://localhost:8080/api/v1/polls/${pollID}/nominations`, {
      headers: {
        "Authorization": "Bearer " + (localStorage.getItem("userToken") || "")
      }
    });
  }
}
