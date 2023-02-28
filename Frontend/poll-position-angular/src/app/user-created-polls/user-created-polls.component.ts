import { Component } from '@angular/core';
import {Poll} from "../models/poll";

@Component({
  selector: 'app-user-created-polls',
  templateUrl: './user-created-polls.component.html',
  styleUrls: ['./user-created-polls.component.css']
})
export class UserCreatedPollsComponent {
  polls: Poll[] = [];
}
