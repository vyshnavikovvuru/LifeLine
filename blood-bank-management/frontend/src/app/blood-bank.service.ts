
import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Donor {
  id?: number;
  name: string;
  bloodGroup: string;
  phone: string;
  city: string;
  age: number;
}

export interface BloodRequest {
  id?: number;
  patientName: string;
  bloodGroup: string;
  hospital: string;
  contactPhone: string;
  units: number;
  status?: string;
  createdAt?: string;
}

export interface BloodInventory {
  id?: number;
  bloodGroup: string;
  unitsAvailable?: number;
  units?: number;
}

export interface SupportQuery {
  name: string;
  category: string;
  message: string;
}

@Injectable({ providedIn: 'root' })
export class BloodBankService {
  private http = inject(HttpClient);

  private api = 'http://localhost:8080/api';

  // Donor APIs

  getDonors(bloodGroup = ''): Observable<Donor[]> {
    let params = new HttpParams();

    if (bloodGroup.trim()) {
      params = params.set('bloodGroup', bloodGroup.trim());
    }

    return this.http.get<Donor[]>(`${this.api}/donors`, { params });
  }

  addDonor(donor: Donor): Observable<Donor> {
    return this.http.post<Donor>(`${this.api}/donors`, donor);
  }

  deleteDonor(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/donors/${id}`);
  }

  // Blood request APIs

  getRequests(): Observable<BloodRequest[]> {
    return this.http.get<BloodRequest[]>(`${this.api}/requests`);
  }

  addRequest(request: BloodRequest): Observable<BloodRequest> {
    return this.http.post<BloodRequest>(
      `${this.api}/requests`,
      request
    );
  }

  approveOrRejectRequest(
    id: number,
    status: 'APPROVED' | 'REJECTED'
  ): Observable<BloodRequest> {
    return this.http.patch<BloodRequest>(
      `${this.api}/requests/${id}/status`,
      { status }
    );
  }

  fulfillRequest(id: number): Observable<BloodRequest> {
    return this.http.patch<BloodRequest>(
      `${this.api}/requests/${id}/fulfill`,
      {}
    );
  }

  // Blood inventory APIs

  getInventory(): Observable<BloodInventory[]> {
    return this.http.get<BloodInventory[]>(
      `${this.api}/inventory`
    );
  }

  getLowStockInventory(): Observable<BloodInventory[]> {
    return this.http.get<BloodInventory[]>(
      `${this.api}/inventory/low-stock`
    );
  }

  addStock(
    bloodGroup: string,
    units: number
  ): Observable<BloodInventory> {
    return this.http.post<BloodInventory>(
      `${this.api}/inventory/stock`,
      { bloodGroup, units }
    );
  }

  // Support APIs

  submitSupportQuery(
    query: SupportQuery
  ): Observable<SupportQuery> {
    return this.http.post<SupportQuery>(
      `${this.api}/support`,
      query
    );
  }
}
