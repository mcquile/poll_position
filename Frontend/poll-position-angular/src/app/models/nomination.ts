import {Poll} from "./poll";
import {User} from "./user";

export interface Nomination{
  nominationID: number;
  poll: Poll;
  nominee: string;
  nominator: User;
}
