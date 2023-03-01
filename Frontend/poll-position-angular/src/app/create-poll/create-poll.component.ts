import { Component } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-create-poll',
  templateUrl: './create-poll.component.html',
  styleUrls: ['./create-poll.component.css']
})
export class CreatePollComponent {
  createPollForm = new FormGroup({
    title: new FormControl(''),
    description: new FormControl(''),
    startTime: new FormControl(''),
    endTime: new FormControl('')
  });

  createPoll(): void {
    console.log(this.createPollForm.value);
  }
}
