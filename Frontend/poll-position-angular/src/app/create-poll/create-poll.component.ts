import { Component } from '@angular/core';
import {FormArray, FormControl, FormGroup} from "@angular/forms";

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

  nomineeForm = new FormGroup({
    nominees: new FormArray([
      new FormGroup({
        nominee: new FormControl(''),
      }),
      new FormGroup({
        nominee: new FormControl(''),
      })
    ])
  });

  createPoll(): void {
    console.log(this.createPollForm.value);
  }


  get nominees(): FormArray {
    return this.nomineeForm.get('nominees') as FormArray;
  }

  addNominee() {
    this.nominees.push(
      new FormGroup({
        nominee: new FormControl(''),
      })
    );
  }
}
