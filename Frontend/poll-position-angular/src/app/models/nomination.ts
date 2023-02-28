import {Poll} from "./poll";
import {User} from "./user";

export interface Nomination{
  nominationID: number;
  pollID: Poll;
  nominee: string;
  nominator: User;
}
