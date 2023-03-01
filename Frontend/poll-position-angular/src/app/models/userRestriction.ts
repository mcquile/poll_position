import {Sex} from "./sex";
import {Branch} from "./branch";
import {Poll} from "./poll";

export interface UserRestriction{
  userRestrictionID: number;
  firstNamePattern: string;
  lastNamePattern: string;
  sexRestrictedTo: Sex;
  branchRestrictedTo: Branch;
  dateOfBirthYoungerThan: Date;
  dateOfBirthOlderThan: Date;
  poll: Poll;
}
