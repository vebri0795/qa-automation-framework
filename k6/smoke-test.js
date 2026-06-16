import http from 'k6/http';
import { check, sleep } from 'k6';

// "options" configures HOW the load test runs: how many virtual users
// (VUs), for how long, and what counts as pass/fail.
export const options = {
  vus: 5,           // 5 virtual users running concurrently
  duration: '15s',  // for 15 seconds

  // Thresholds: pass/fail criteria for the WHOLE run, not a single
  // If these aren't met, k6 exits with a non-zero status code
  thresholds: {
    http_req_duration: ['p(95)<500'], // 95% of requests must complete under 500ms
    http_req_failed: ['rate<0.01'],    // less than 1% of requests may fail
  },
};

// Code each virtual user repeatedly
// executes, in a loop, for the whole duration of the test.
export default function () {
  const response = http.get('https://jsonplaceholder.typicode.com/posts/1');

  // check() is K6's equivalent of an assert, but it doesn't stop
  // execution on failure, it just records pass/fail for reporting.
  check(response, {
    'status is 200': (r) => r.status === 200,
    'response has a title field': (r) => JSON.parse(r.body).title !== undefined,
  });

  // Pause for 1 second between iterations, to simulate a more realistic
  // user pace instead of hammering the API as fast as possible.
  sleep(1);
}
