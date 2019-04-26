import { TestBed } from '@angular/core/testing';

import { ManagerRequestsService } from './manager-requests-service';

describe('ManagerRequestsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ManagerRequestsService = TestBed.get(ManagerRequestsService);
    expect(service).toBeTruthy();
  });
});
