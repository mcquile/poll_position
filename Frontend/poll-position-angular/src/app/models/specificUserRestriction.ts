import {Poll} from "./poll";
import {User} from "./user";

export interface SpecificUserRestriction{
  specificUserRestrictionID: number;
  poll: Poll;
  user: User;
  restricted: boolean;
}
