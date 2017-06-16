angular.module('upsConsole')
  .controller('ActivityController', function ( $log, $timeout, $interval, $modal, variantModal, $scope, metricsEndpoint ) {

    var self = this;

    this.TOOLTIP_TARGETS = "Targeted devices or topics for Android/Firebase";
    this.TOOLTIP_OPENED = "Number of users that launched the mobile app by using the push notification";
    this.TOOLTIP_SUCCESS = "Payload submitted to 3rd party push network for further processing";
    this.TOOLTIP_FAIL = "Could not submit payload to 3rd party";

    this.app = $scope.$parent.$parent.appDetail.app;
    this.metrics = [];
    this.totalCount;
    this.currentPage = 1;
    this.currentStart = 0;
    this.currentEnd = 0;
    this.perPage = 10;
    this.searchString = '';
    this.activeSearch = '';

    var refreshInterval;

    /**
     * Fetches new data, reflecting provided page and searchString
     */
    function fetchMetrics( page, searchString ) {
      return metricsEndpoint.fetchApplicationMetrics(self.app.pushApplicationID, searchString, page, self.perPage)
        .then(function( data ) {
          self.activeSearch = searchString;
          self.metrics.forEach(function( originalMetric ) {
            data.pushMetrics.some(function ( newMetric ) {
              if (originalMetric.id === newMetric.id && originalMetric.$toggled) {
                newMetric.$toggled = true;
                return true;
              }
            });
          });
          self.metrics = data.pushMetrics;
          self.totalCount = data.totalItems;
          self.currentStart = self.perPage * (self.currentPage - 1) + 1;
          self.currentEnd = self.perPage * (self.currentPage - 1) + self.metrics.length;
          self.metrics.forEach(function( metric ) {
            try {
              metric.$message = JSON.parse(metric.rawJsonMessage);
            } catch (err) {
              console.log('failed to parse metric');
              metric.$message = {};
            }
          });
        });
    }

    /**
     * Determines whether search is active - either the user typed search string or the data doesn't reflect the search string yet.
     *
     * @return false if searchString if false and data reflects that searchString; true otherwise
     */
    this.isSearchActive = function() {
      return self.searchString || self.activeSearch;
    };

    /**
     * Fetches new data on page change
     */
    this.onPageChange = function ( page ) {
      fetchMetrics( page, self.searchString );
    };

    function getVariantByID ( variantID ) {
      return self.app.variants.filter(function( variant ) {
        return variant.variantID == variantID;
      })[0];
    }

    function refreshUntilAllServed() {
      fetchMetrics( self.currentPage, self.searchString )
        .then(function() {
          $log.debug('refreshed');
        });
    }

    // initial load
    refreshUntilAllServed();

    $scope.$on('upsNotificationSent', function() {
      var timer1 = $timeout(refreshUntilAllServed, 3000); // refresh again to be double-sure ;-) note: should be addressed as part of https://issues.jboss.org/browse/AGPUSH-1513
      // destroy timeouts
      $scope.$on("$destroy", function() {
        $log.debug('cancelling refreshUntilAllServed timeouts');
        $timeout.cancel( timer1 );
      });
    });
    $scope.$on('$destroy', function () {
      if (refreshInterval) {
        $log.debug('cancelling refreshInterval');
        $interval.cancel(refreshInterval);
      }
    });

    $scope.$watch(function() { return self.searchString }, function( searchString ) {
      self.currentPage = 1;
      fetchMetrics( self.currentPage, self.searchString );
    });

  });
