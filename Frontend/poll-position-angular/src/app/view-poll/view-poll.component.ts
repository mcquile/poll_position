import { Component } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Poll} from "../models/poll";
import {HttpClient} from "@angular/common/http";
import {User} from "../models/user";
import {Branch} from "../models/branch";
import {Sex} from "../models/sex";

@Component({
  selector: 'app-view-poll',
  templateUrl: './view-poll.component.html',
  styleUrls: ['./view-poll.component.css']
})
export class ViewPollComponent {

  id: string;

  poll: Poll;

  constructor(private route: ActivatedRoute, private http: HttpClient) {
    this.id=""
    this.poll = {
      description: "not real",
      nominationEndTime: new Date(),
      pollCreationTime: new Date(),
      pollCreator: new class implements User {
        branch= new class implements Branch{
          branchID=-1;
          branchName= "string";

        };
        dateOfBirth= new Date();
        email= "string";
        firstName= "string";
        lastName= "string";
        profilePicLink= "string";
        role="string";
        sex= new class implements Sex{
          name="";
          sexID=-1;
        };
        userID= "string";
      },
      pollID: "nothing here",
      title: "Very Fake",
      voteEnd: new Date(),
      voteStart: new Date()
    }
    this.route.params.subscribe( params => {
      this.id = params['id']
      http.get<Poll>(`localhost:8080/api/v1/polls/${this.id}`).subscribe(poll => this.poll=poll)
    });

  }

  ngOnInit(): void{

  }
}
