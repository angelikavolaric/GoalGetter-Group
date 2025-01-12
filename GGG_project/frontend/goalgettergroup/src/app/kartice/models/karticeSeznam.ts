import {Kartica} from './kartica';

export interface KarticeSeznam {
  id: number;
  predmet: string;
  opis: string;
  ustvarjen: Date;
  kartice: Kartica[];}

/*import { Kartica } from './kartica';

export class KarticeSeznam {
  id: number;
  predmet: string;
  opis: string;
  ustvarjen: Date;
  kartice: Kartica[];

  constructor(id: number, predmet: string, opis: string, ustvarjen: Date, kartice: Kartica[]) {
    this.id = id;
    this.predmet = predmet;
    this.opis = opis;
    this.ustvarjen = ustvarjen;
    this.kartice = kartice;
  }
}*/

