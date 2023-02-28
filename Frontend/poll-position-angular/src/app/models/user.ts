import {Branch} from "./branch";
import {Sex} from "./sex";

export interface User{
  userID: string;
  firstName: string;
  lastName: string;
  branchID: Branch;
  dateOfBirth: Date;
  profilePicLink: string;
  email: string;
  sexID: Sex;
  role: string;
}
