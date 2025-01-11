import { KarticeSeznam } from './karticeSeznam';

export class Kartica {
  id: number;
  vprasanje: string;
  odgovor: string;
  karticeSeznamId: number;

  constructor(id: number, vprasanje: string, odgovor: string, karticeSeznamId: number) {
    this.id = id;
    this.vprasanje = vprasanje;
    this.odgovor = odgovor;
    this.karticeSeznamId = karticeSeznamId;
  }
}
