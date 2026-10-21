import { Component, OnInit } from '@angular/core';

import { HttpErrorResponse } from '@angular/common/http';

import { CommonModule } from '@angular/common';

import { FormsModule } from '@angular/forms';

import {

  BloodBankService,

  Donor,

  BloodRequest,

  BloodInventory

} from './blood-bank.service';

@Component({

  selector: 'app-root',

  standalone: true,

  imports: [CommonModule, FormsModule],

  templateUrl: './app.component.html',

  styleUrl: './app.component.css'

})

export class AppComponent implements OnInit {


activeTab:

  | 'dashboard'

  | 'donors'

  | 'requests'

  | 'inventory'

  | 'help' = 'dashboard';

  donors: Donor[] = [];

  requests: BloodRequest[] = [];

  inventory: BloodInventory[] = [];

  loading = false;

  errorMessage = '';

  successMessage = '';

  showDonorModal = false;

  showRequestModal = false;

  showStockModal = false;

  showModal = false;

  modalType = '';

  donorForm = {

    name: '',

    age: 18,

    bloodGroup: 'A+',

    phone: '',

    city: '',

    gender: '',

    email: '',

    address: ''

  };

  requestForm = {

    patientName: '',

    bloodGroup: 'A+',

    units: 1,

    hospital: '',

    contactPhone: ''

  };

  stockForm = {

    bloodGroup: 'A+',

    units: 1

  };


  supportForm = {

    name: '',

    category: 'General Query',

    message: ''

  };

  supportLoading = false;

  isQuerySubmitted = false;
bloodGroups: string[] = [

    'A+', 'A-', 'B+', 'B-',

    'AB+', 'AB-', 'O+', 'O-'

  ];

  constructor(private bloodBankService: BloodBankService) {}

  ngOnInit(): void {

    this.loadDashboard();

  }

  loadDashboard(): void {

    this.loadDonors();

    this.loadRequests();

    this.loadInventory();

  }

  setActiveTab(

    tab: 'dashboard' | 'donors' | 'requests' | 'inventory' | 'help'

  ): void {

    this.activeTab = tab;

    this.clearMessages();

    if (tab === 'dashboard') {

      this.loadDashboard();

    } else if (tab === 'donors') {

      this.loadDonors();

    } else if (tab === 'requests') {

      this.loadRequests();

    } else if (tab === 'inventory') {

      this.loadInventory();

    }

  }


  loadDonors(): void {

    this.bloodBankService.getDonors().subscribe({

      next: (data) => {

        this.donors = data;

      },

      error: (error: HttpErrorResponse) => {

        console.error('Error loading donors:', error);

        this.errorMessage = 'Unable to load donors.';

      }

    });

  }


  addDonor(): void {

    this.clearMessages();

    const name = this.donorForm.name.trim();

    const phone = this.donorForm.phone.trim();

    const city = this.donorForm.city.trim();

    const age = Number(this.donorForm.age);

    if (

      !name ||

      !phone ||

      !city ||

      !this.donorForm.bloodGroup

    ) {

      this.errorMessage =

        'Please fill in all required donor details.';

      return;

    }

    if (!/^[a-zA-Z\s.'-]+$/.test(name)) {

      this.errorMessage = 'Please enter a valid donor name.';

      return;

    }

    if (!Number.isInteger(age) || age < 18 || age > 65) {

      this.errorMessage =

        'Donor age must be between 18 and 65.';

      return;

    }

    if (!/^\d{10}$/.test(phone)) {

      this.errorMessage =

        'Please enter a valid 10-digit phone number.';

      return;

    }

    if (!this.bloodGroups.includes(this.donorForm.bloodGroup)) {

      this.errorMessage = 'Please select a valid blood group.';

      return;

    }

    const donor: Donor = {

      name,

      age,

      bloodGroup: this.donorForm.bloodGroup,

      phone,

      city

    };

    this.bloodBankService.addDonor(donor).subscribe({

      next: () => {

        this.successMessage = 'Donor registered successfully!';

        this.closeAllModals();

        this.resetDonorForm();

        this.loadDonors();

      },

      error: (error: HttpErrorResponse) => {

        console.error('Error registering donor:', error);

        this.errorMessage =

          error.error?.message || 'Failed to register donor.';

      }

    });

  }

  deleteDonor(id: number | undefined): void {

    if (id === undefined) {

      this.errorMessage = 'Cannot delete donor: ID is missing.';

      return;

    }

    const confirmed = confirm(

      'Are you sure you want to delete this donor?'

    );

    if (!confirmed) {

      return;

    }

    this.clearMessages();

    this.bloodBankService.deleteDonor(id).subscribe({

      next: () => {

        this.successMessage = 'Donor deleted successfully!';

        this.loadDonors();

      },

      error: (error: HttpErrorResponse) => {

        console.error('Error deleting donor:', error);

        this.errorMessage =

          error.error?.message || 'Failed to delete donor.';

      }

    });

  }

  resetDonorForm(): void {

    this.donorForm = {

      name: '',

      age: 18,

      bloodGroup: 'A+',

      phone: '',

      city: '',

      gender: '',

      email: '',

      address: ''

    };

  }


  loadRequests(): void {

    this.bloodBankService.getRequests().subscribe({

      next: (data) => {

        this.requests = data;

      },

      error: (error: HttpErrorResponse) => {

        console.error('Error loading requests:', error);

        this.errorMessage = 'Unable to load blood requests.';

      }

    });

  }

  addRequest(): void {

    this.clearMessages();

    if (

      !this.requestForm.patientName.trim() ||

      !this.requestForm.hospital.trim() ||

      !this.requestForm.contactPhone.trim() ||

      Number(this.requestForm.units) < 1

    ) {

      this.errorMessage =

        'Please enter valid blood request details.';

      return;

    }


    if (!/^\d{10}$/.test(this.requestForm.contactPhone.trim())) {

      this.errorMessage =

        'Please enter a valid 10-digit contact number.';

      return;

    }


    if (

      !Number.isInteger(Number(this.requestForm.units)) ||

      !Number.isFinite(Number(this.requestForm.units))

    ) {

      this.errorMessage =

        'Units must be a valid positive whole number.';

      return;

    }

    if (!this.bloodGroups.includes(this.requestForm.bloodGroup)) {

      this.errorMessage = 'Please select a valid blood group.';

      return;

    }

    const request: BloodRequest = {

      patientName: this.requestForm.patientName.trim(),

      bloodGroup: this.requestForm.bloodGroup,

      units: Number(this.requestForm.units),

      hospital: this.requestForm.hospital.trim(),

      contactPhone: this.requestForm.contactPhone.trim()

    };


    this.bloodBankService.addRequest(request).subscribe({

      next: () => {

        this.successMessage =

          'Blood request submitted successfully!';

        this.closeAllModals();

        this.resetRequestForm();

        this.loadRequests();

      },

      error: (error: HttpErrorResponse) => {

        console.error('Error submitting request:', error);

        this.errorMessage =

          error.error?.message ||

          'Failed to submit blood request.';

      }

    });

  }

  updateRequestStatus(

    id: number,

    status: 'APPROVED' | 'REJECTED'

  ): void {

    this.clearMessages();

    this.bloodBankService

      .approveOrRejectRequest(id, status)

      .subscribe({

        next: () => {

          this.successMessage =

            status === 'APPROVED'

              ? 'Blood request approved successfully!'

              : 'Blood request rejected.';

          this.loadRequests();

        },

        error: (error: HttpErrorResponse) => {

          console.error('Error updating request status:', error);

          this.errorMessage =

            error.error?.message ||

            `Failed to ${status.toLowerCase()} the request.`;

        }

      });

  }

  fulfillRequest(id: number): void {

    const confirmed = confirm(

      'Fulfill this approved request and deduct the required units from inventory?'

    );

    if (!confirmed) {

      return;

    }

    this.clearMessages();

    this.bloodBankService.fulfillRequest(id).subscribe({

      next: () => {

        this.successMessage =

          'Request fulfilled successfully! Inventory has been updated.';

        this.loadRequests();

        this.loadInventory();

      },

      error: (error: HttpErrorResponse) => {

        console.error('Error fulfilling request:', error);

        this.errorMessage =

          error.error?.message ||

          'Failed to fulfill request. Please check the available stock.';

      }

    });

  }

  resetRequestForm(): void {

    this.requestForm = {

      patientName: '',

      bloodGroup: 'A+',

      units: 1,

      hospital: '',

      contactPhone: ''

    };

  }

  //*&#x20;==================== INVENTORY METHODS ====================*

  loadInventory(): void {

    this.bloodBankService.getInventory().subscribe({

      next: (data) => {

        this.inventory = data;

      },

      error: (error: HttpErrorResponse) => {

        console.error('Error loading inventory:', error);

        this.errorMessage = 'Unable to load blood inventory.';

      }

    });

  }

  addStock(): void {

    this.clearMessages();

    const units = Number(this.stockForm.units);

    if (

      !Number.isInteger(units) ||

      !Number.isFinite(units) ||

      units < 1

    ) {

      this.errorMessage =

        'Units to add must be a positive whole number.';

      return;

    }

    if (!this.bloodGroups.includes(this.stockForm.bloodGroup)) {

      this.errorMessage = 'Please select a valid blood group.';

      return;

    }

    this.bloodBankService

      .addStock(this.stockForm.bloodGroup, units)

      .subscribe({

        next: () => {

          this.successMessage =

            'Blood stock updated successfully!';

          this.closeAllModals();

          this.stockForm = {

            bloodGroup: 'A+',

            units: 1

          };

          this.loadInventory();

        },

        error: (error: HttpErrorResponse) => {

          console.error('Error adding stock:', error);

          this.errorMessage =

            error.error?.message ||

            'Failed to update blood stock.';

        }

      });

  }


  submitSupportQuery(): void {
    this.clearMessages();

    const name = this.supportForm.name.trim();
    const category = this.supportForm.category;
    const message = this.supportForm.message.trim();

    if (!name || !category || !message) {
      this.errorMessage = 'Please fill in all support form fields.';
      return;
    }

    if (name.length > 100 || message.length > 1000) {
      this.errorMessage =
        'Name must be under 100 characters and message under 1000 characters.';
      return;
    }

    this.isQuerySubmitted = true;
    this.successMessage = '';
    this.errorMessage = '';
  }


  get totalDonors(): number {

    return this.donors.length;

  }

  get totalRequests(): number {

    return this.requests.length;

  }

  get totalUnits(): number {

    return this.inventory.reduce(

      (total, item) =>

        total + Number(item.unitsAvailable ?? item.units ?? 0),

      0

    );

  }

  get lowStockInventory(): BloodInventory[] {

    return this.inventory.filter(

      item => Number(item.unitsAvailable ?? item.units ?? 0) < 5

    );

  }

  getBloodGroupUnits(bloodGroup: string): number {

    const item = this.inventory.find(

      inventoryItem =>

        inventoryItem.bloodGroup.toUpperCase() ===

        bloodGroup.toUpperCase()

    );

    return Number(item?.unitsAvailable ?? item?.units ?? 0);

  }

  getStockPercentage(bloodGroup: string): number {

    const units = this.getBloodGroupUnits(bloodGroup);


    return Math.min((units / 20) * 100, 100);

  }

  getStockStatus(units: number): string {

    if (units === 0) {

      return 'Out of Stock';

    }

    if (units < 5) {

      return 'Low Stock';

    }

    return 'Available';

  }


  openDonorModal(): void {

    this.clearMessages();

    this.showDonorModal = true;

    this.showRequestModal = false;

    this.showStockModal = false;

    this.showModal = true;

    this.modalType = 'donor';

  }

  openRequestModal(): void {

    this.clearMessages();

    this.showRequestModal = true;

    this.showDonorModal = false;

    this.showStockModal = false;

    this.showModal = true;

    this.modalType = 'request';

  }

  openStockModal(): void {

    this.clearMessages();

    this.showStockModal = true;

    this.showDonorModal = false;

    this.showRequestModal = false;

    this.showModal = true;

    this.modalType = 'stock';

  }

  closeAllModals(): void {

    this.showDonorModal = false;

    this.showRequestModal = false;

    this.showStockModal = false;

    this.showModal = false;

    this.modalType = '';

  }


  clearMessages(): void {

    this.errorMessage = '';

    this.successMessage = '';

  }

}
