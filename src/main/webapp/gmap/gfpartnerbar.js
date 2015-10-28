/**
 * Copyright (c) 2008 Google Inc.
 *
 * You are free to copy and use this sample.
 * License can be found here: http://code.google.com/apis/ajaxsearch/faq/#license
*/

(function () {
function PartnerBar(container, partners, opt_options) {
  this.numEntries = 2;
  this.linkTarget = google.feeds.LINK_TARGET_BLANK;
  this.horizontalMode = true;
  this.headerTitle = null;
  this.semanticMarkup = false;
  this.classmap = classmap;
  this.footerTitle = null;
  this.footerLink = null;
  this.autoAdjustOnFailure = false;
  this.autoAdjust = false;
  this.cycleTime = PartnerBar.DEFAULT_CYCLE_TIME;
  this.cycleNext = 0;
  this.cycleTimer = null;
  this.autoRefresh = false;
  this.partnerDoms = new Array();
  this.cacheMode = true;

  if (opt_options) {
    if (opt_options.linkTarget) {
      this.linkTarget = opt_options.linkTarget;
    }
    if (opt_options.numEntries) {
      this.numEntries = opt_options.numEntries;
    }
    if (opt_options.headerTitle) {
      this.headerTitle = opt_options.headerTitle;
    }
    if (opt_options.footerTitle) {
      this.footerTitle = opt_options.footerTitle;
      if (opt_options.footerLink) {
        this.footerLink = opt_options.footerLink;
      }
    }
    if (opt_options.semanticMarkup) {
      this.semanticMarkup = opt_options.semanticMarkup;
    }
    if (opt_options.autoAdjustOnFailure) {
      this.autoAdjustOnFailure = opt_options.autoAdjustOnFailure;
    }
    if (opt_options.autoAdjust) {
      this.autoAdjust = opt_options.autoAdjust;
      if (this.autoAdjust == true) {
        this.autoAdjustOnFailure = true;
      }
    }
    if (opt_options.randomSelectionCount) {
      this.randomSelectionCount = opt_options.randomSelectionCount;
    }
    if (opt_options.autoRefresh) {
      this.autoRefresh = opt_options.autoRefresh;
    }
    if (opt_options.cycleTime) {
      var ct = opt_options.cycleTime;
      if (ct < PartnerBar.CYCLE_TIME_MIN) {
        ct = PartnerBar.DEFAULT_CYCLE_TIME;
      } else if (ct > PartnerBar.CYCLE_TIME_MAX) {
        ct = PartnerBar.DEFAULT_CYCLE_MAX;
      } else {
        // in range
        ct = opt_options.cycleTime;
      }
      this.cycleTime = ct;
    }
  }

  // browser fun
  if (window.ActiveXObject) this.ie = this[window.XMLHttpRequest ? 'ie7' : 'ie6'] = true;

  // clone the partner array so that callers may re-use
  this.partners = this.clonePartners(partners);
  this.originalPartners = this.clonePartners(partners);
  this.selectPartners();

  // now build out the containers
  if (typeof container == 'string') {
    container = document.getElementById(container);
  }

  this.refreshClosure = this.methodClosure(this, this.refresh, []);
  this.container = container;
  this.buildContainers();
  this.loadPartners();

  if (this.autoRefresh) {
    this.launchAutoRefresh();
  }
}

// expose the object
window.PartnerBar = PartnerBar;

PartnerBar.CYCLE_TIME_MIN = 5000;
PartnerBar.CYCLE_TIME_MAX = 300000;

PartnerBar.CYCLE_TIME_EXTRA_SHORT = 5000;
PartnerBar.CYCLE_TIME_SHORT = 7500;
PartnerBar.CYCLE_TIME_MEDIUM = 15000;
PartnerBar.CYCLE_TIME_LONG = 30000;
PartnerBar.DEFAULT_CYCLE_TIME = PartnerBar.CYCLE_TIME_LONG;

PartnerBar.prototype.refresh = function() {
  this.cssSetClass(this.root, ["pb-root"], null);
  this.partners = this.clonePartners(this.originalPartners);
  this.selectPartners();
  this.loadPartners();
}

PartnerBar.prototype.clearTimers = function() {
  if (this.cycleTimer) {
      clearTimeout(this.cycleTimer);
      this.cycleTimer = null;
  }
}

PartnerBar.prototype.launchAutoRefresh = function() {
  this.clearTimers();
  this.cycleTimer = setTimeout(this.refreshClosure, this.cycleTime);
}

PartnerBar.prototype.selectPartners = function() {
  if (this.randomSelectionCount) {
    var max = Math.max(1, Math.min(this.randomSelectionCount, this.partners.length));
    var pickFrom = this.partners;
    this.partners = new Array();

    while (this.partners.length < max) {
      var maxIndex = pickFrom.length - 1;
      var index = Math.round(maxIndex * Math.random());
      var candidate = pickFrom[index];

      // now, see if we have a pre-built partner cell
      if (this.partnerDoms.length > 0) {
        var partnerDom = this.partnerDoms.pop();

        candidate.root = partnerDom.root;
        candidate.logoHeader = partnerDom.logoHeader;
        candidate.feedData = partnerDom.feedData;
        candidate.moreLink = partnerDom.moreLink;
      }

      // remove from pickFrom and add to partners
      pickFrom.splice(index,1);
      this.partners.push(candidate);
    }
  } else {
    // not using random selection so we still need to get a dom for this partner
    // assuming its from a a refresh
    for (var i=0; i<this.partners.length; i++) {
      var partner = this.partners[i];
      if (this.partnerDoms.length > 0) {
        var partnerDom = this.partnerDoms.pop();
        partner.root = partnerDom.root;
        partner.logoHeader = partnerDom.logoHeader;
        partner.feedData = partnerDom.feedData;
        partner.moreLink = partnerDom.moreLink;
      }
    }
  }
  this.failureCount = 0;
  this.expectedPartnerCount = this.partners.length;

  // todo(markl): move this. should not need to rebuild the
  // partners dom's like this.
  for (var i=0; i<this.partners.length; i++) {
    var partner = this.partners[i];
    if (partner.root && partner.logoHeader && partner.feedData &&
        partner.moreLink) {
      var partnerDom = {
        root : partner.root,
        logoHeader : partner.logoHeader,
        feedData : partner.feedData,
        moreLink : partner.moreLink
        };
      this.partnerDoms.push(partnerDom);
    }
  }
}

PartnerBar.prototype.resetClassOnPartnerDom = function(partner) {
  var classNames;

  // root, logoHeader, feedData, moreLink
  classNames = this.getClassNames("pb-partner-cell", partner.classSuffix);
  this.cssSetClass(partner.root, classNames);
  classNames = this.getClassNames("pb-partner-logo", partner.classSuffix);
  this.cssSetClass(partner.logoHeader, classNames);
  classNames = this.getClassNames("pb-partner-feedData", partner.classSuffix);
  this.cssSetClass(partner.feedData, classNames);
  classNames = this.getClassNames("pb-partner-moreLink", partner.classSuffix);
  this.cssSetClass(partner.moreLink, classNames);
}


/**
 * create a feed object for each partner, fire it up,
 * and then draw results in the completion handler
 */
PartnerBar.prototype.loadPartners = function() {
  var setWidth = -1;
  if (this.autoAdjust && this.partners.length > 0) {
    var margins = this.grabComputedPartnerMargins();
    var marginTotal = (margins.ml + margins.mr) * this.partners.length;
    var totalWidth = this.root.offsetWidth - marginTotal;
    // set width for partner cells automatically.
    var numPartners = this.partners.length + (this.ie6 ? 1 : 0);
    setWidth = Math.floor(totalWidth / numPartners);
  }
  for (var i=0; i<this.partners.length; i++) {
    var partner = this.partners[i];
    if (this.cacheMode && partner.feedObj && partner.result) {
      partner.cacheHitCount++;
    } else {
      partner.feedObj = new google.feeds.Feed(partner.feed);
      partner.feedObj.setNumEntries(this.numEntries);
      if (partner.logoResolver) {
        partner.feedObj.setResultFormat(google.feeds.Feed.MIXED_FORMAT);
      }
    }
    this.loadPartner(partner);
    if (setWidth > 0) {
      partner.root.style.width = setWidth + 'px';
    }
  }

  if (this.autoRefresh) {
    this.launchAutoRefresh();
  }
}

PartnerBar.prototype.loadPartner = function(partner) {
  if (this.cacheMode && partner.feedObj && partner.result && !partner.result.error) {
    this.drawPartner(partner);
  } else {
    var me = this;
    partner.feedObj.load(function(result) {
      partner.result = result;
      me.drawPartner(partner);
    });
  }
}

PartnerBar.prototype.drawPartner = function(partner) {
  if (!partner.result.error) {
    if (this.cacheMode) {
      this.originalPartners[partner.index].feedObj = partner.feedObj;
      this.originalPartners[partner.index].result = partner.result;
    }
    // this is only needed if we do periodic refresh
    // for a home page application like this, its not
    // really worthwhile, but i'll keep them here just
    // in case time life wants to do periodic changes,
    // swap partners on the fly, etc
    this.removeChildren(partner.logoHeader);
    this.removeChildren(partner.feedData);
    this.removeChildren(partner.moreLink);

    //
    // alter behavior of logoLink
    var subtitleMode = false;
    if (partner.title && partner.subtitle) {
      subtitleMode = true;
    }
    // draw the logo
    var logoLink = null;
    if (subtitleMode) {
      logoLink = this.createElement("pb-partner-logo", null,
                                    partner.classSuffix);
    } else {
      logoLink = this.createLink(partner.link, null,
                                 this.linkTarget,
                                 this.getClassNames("pb-partner-logo",
                                                    partner.classSuffix));
    }
    if ( partner.title ) {
      // if we just have a title field, preserve original design,
      // if we have .subtitle, then use new model dom
      if (partner.subtitle) {
        // title first
        var content = this.createElement("pb-logo-title", partner.title,
                                         partner.classSuffix);
        var link = this.createLink(partner.link, null,
                                   this.linkTarget,
                                   this.getClassNames("pb-partner-logo",
                                                      partner.classSuffix));
        link.appendChild(content);
        logoLink.appendChild(link);

        // subtitle next
        content = this.createElement("pb-logo-subtitle", partner.subtitle,
                                     partner.classSuffix);
        link = this.createLink(partner.link, null,
                               this.linkTarget,
                               this.getClassNames("pb-partner-logo",
                                                  partner.classSuffix));
        link.appendChild(content);
        logoLink.appendChild(link);
      } else {
        logoLink.innerHTML = partner.title;
      }
    }
    if (partner.logo || partner.logoResolver) {
      var logoImg;
      if (partner.logoResolver) {
        partner.logo = partner.logoResolver(partner);
      }
      logoImg = this.createImage(partner.logo,
                                 this.getClassNames("pb-partner-logo",
                                                    partner.classSuffix));
      logoLink.appendChild(logoImg);
    }
    partner.logoHeader.appendChild(logoLink);

    // draw in the feed entries
    for (var i=0; i<partner.result.feed.entries.length; i++) {
      var entry = partner.result.feed.entries[i];
      var entryBlock = this.createElement("pb-entry", null, partner.classSuffix);
      var divLink = this.createLink(entry.link, entry.title,
                                    this.linkTarget, "pb-title", true);
      entryBlock.appendChild(divLink);
      partner.feedData.appendChild(entryBlock);
    }

    var moreBlock = this.createElement("pb-partner-moreLink", null,
                                       partner.classSuffix);

    // draw in the more link
    var moreLink = this.createLink(partner.link, partner.moreTitle,
                                  this.linkTarget, "pb-partner-moreLink");
    moreBlock.appendChild(moreLink);
    partner.moreLink.appendChild(moreBlock);
    this.resetClassOnPartnerDom(partner);
  } else {
    // Adjust sizing if option is set..
    if (this.autoAdjustOnFailure) {
      var goodExpected = this.partners.length-this.failureCount;
      var totalWidth = goodExpected * partner.root.offsetWidth;
      var newPartnerWidth = Math.floor(totalWidth / (goodExpected-1));
      for (var i=0; i<this.partners.length; i++) {
        this.partners[i].root.style.width = newPartnerWidth + "px";
      }
    }

    // enable no footprint on no/error data
    this.failureCount++;
    if (this.failureCount >= this.expectedPartnerCount) {
      this.cssSetClass(this.root, ["pb-root", "pb-noResults"],
                       null);
    } else {
      this.resetClassOnPartnerDom(partner);
      this.cssSetClass(partner.root, ["pb-partner-cell", "pb-noResults"],
           partner.classSuffix);
    }
  }
}

PartnerBar.prototype.clonePartners = function(partners) {
  var cloned = new Array();
  for (var i=0; i<partners.length; i++) {
    var partner = partners[i];
    var p = new Object();
    if (partner.title) {
      p.title = partner.title;
      if (partner.subtitle) {
        p.subtitle = partner.subtitle;
      }
    }
    if (partner.logo) {
      p.logo = partner.logo;
    } else if (partner.logoResolver) {
      p.logoResolver = partner.logoResolver;
    }
    p.feed = partner.feed;
    p.moreTitle = partner.moreTitle;
    p.link = partner.link;
    if (partner.classSuffix) {
      p.classSuffix = partner.classSuffix;
    } else {
      p.classSuffix = null;
    }
    if (this.cacheMode && partner.feedObj && partner.result) {
      p.feedObj = partner.feedObj;
      p.result = partner.result;
    } else {
      p.feedObj = null;
      p.result = null;
      p.cacheHitCount = 0;
    }
    p.index = i;
    cloned.push(p);
  }
  return cloned;
}
/**
 * Build out the various containers...
 * <container>
 *  <header/>
 *   <partnerTable>
 *    <partnerCell>
 *      <partnerLogo><a><img/></a></partnerLogo>
 *      <partnerFeedData>
 *        <entry/>0..2
 *      </partnerFeedData>
 *      <partnerMoreLink/><a></a></partnerMoreLink>
 *    </partnerCell>
 *    </partnerTable>
 *    <footer/>
 * </container>
 */
PartnerBar.prototype.buildContainers = function() {
  this.root = this.createElement("pb-root");
  this.header = this.createElement("pb-header", this.headerTitle);
  this.footer = this.createElement("pb-footer");
  if (this.footerLink) {
    var link = this.createLink(this.footerLink, this.footerTitle,
                               this.linkTarget, this.getClassNames("pb-footer"));
    this.footer.appendChild(link);
  } else if (this.footerTitle) {
    this.footer.innerHTML = this.footerTitle;
  }

  // create the partner table with one cell per partner
  this.partnerTable = this.createElement("pb-partner-table");
  for (var i=0; i<this.partners.length; i++) {
    var partner = this.partners[i];
    partner.root = this.createElement("pb-partner-cell", null, partner.classSuffix);
    partner.logoHeader = this.createElement("pb-partner-logo", null, partner.classSuffix);
    partner.feedData = this.createElement("pb-partner-feedData", null, partner.classSuffix);
    partner.moreLink = this.createElement("pb-partner-moreLink", null, partner.classSuffix);
    partner.root.appendChild(partner.logoHeader);
    partner.root.appendChild(partner.feedData);
    partner.root.appendChild(partner.moreLink);
    this.partnerTable.appendChild(partner.root);

    var partnerDom = {
      root : partner.root,
      logoHeader : partner.logoHeader,
      feedData : partner.feedData,
      moreLink : partner.moreLink
      };
    this.partnerDoms.push(partnerDom);
  }

  this.root.appendChild(this.header);
  this.root.appendChild(this.partnerTable);
  this.root.appendChild(this.footer);

  // bind to page
  this.removeChildren(this.container);
  this.container.appendChild(this.root);
}

PartnerBar.prototype.cssSetClass = function(el, className, opt_suffix) {
  el.className = this.getClassNames(className, opt_suffix);
}

PartnerBar.prototype.createElement = function(key, opt_text, opt_suffix) {
  var elType = this.getSemanticType(key);
  var el = document.createElement(elType);
  if (opt_text) {
    el.innerHTML = opt_text;
  }
  el.className = this.getClassNames(key, opt_suffix);
  return el;
}

PartnerBar.prototype.createDiv = function(opt_text, opt_className) {
  var el = document.createElement("div");
  if (opt_text) {
    el.innerHTML = opt_text;
  }
  if (opt_className) { el.className = opt_className; }
  return el;
}

PartnerBar.prototype.createImage = function(url, opt_className) {
  var el = document.createElement("img");
  el.src = url;
  if (opt_className) { el.className = opt_className; }
  return el;
}

PartnerBar.prototype.removeChildren = function(parent) {
  while (parent.firstChild) {
    parent.removeChild(parent.firstChild);
  }
}

PartnerBar.prototype.methodClosure = function(object, method, opt_argArray) {
  return function() {
    return method.apply(object, opt_argArray);
  }
}

PartnerBar.prototype.createLink = function(href, opt_text, opt_target,
                                          opt_className, opt_divwrap) {
  var el = document.createElement("a");
  el.href = href;
  if (opt_text) {
    el.appendChild(document.createTextNode(opt_text));
  }
  if (opt_className) {
    el.className = opt_className;
  }
  if (opt_target) {
    el.target = opt_target;
  }
  if (opt_divwrap) {
    var div = this.createDiv(null, opt_className);
    div.appendChild(el);
    el = div;
  }
  return el;
}

var classmap = {
  "pb-root" : {
    type : "div"
  },

  "pb-header" : {
    type : "h3"
  },

  "pb-footer" : {
    type : "div"
  },

  "pb-partner-table" : {
    type : "div"
  },

  "pb-partner-cell" : {
    type : "div"
  },

  "pb-partner-logo" : {
    type : "h4"
  },

  "pb-logo-title" : {
    type : "h4"
  },

  "pb-title" : {
    type : "h4"
  },

  "pb-logo-subtitle" : {
    type : "h5"
  },

  "pb-subtitle" : {
    type : "h5"
  },

  "pb-partner-feedData" : {
    type : "ul"
  },

  "pb-entry" : {
    type : "li"
  },

  "pb-partner-moreLink" : {
    type : "div"
  }
};

PartnerBar.prototype.getClassNames = function(keys, opt_suffix, opt_options) {
  var classNames = new Array();
  var classKeys = new Array();
  var keylist = new Array();

  if (typeof keys == 'string') {
    keylist.push(keys);
  } else {
    keylist = keys;
  }

  // now whip through the keys and build out
  // the resulting classname list
  for (var i=0; i<keylist.length; i++ ) {
    var key = keylist[i];

    // deref the key and then channelize the
    // array of classNames
    var classMapEntry = this.classmap[key];
    if ( classMapEntry && classMapEntry.classNames ) {
      for (var c=0; c<classMapEntry.classNames.length; c++) {
        var className = classMapEntry.classNames[c];
        if (opt_suffix) {
          classNames.push(className + "-" + opt_suffix);
        }
      }
    }

    if (opt_options && opt_options.isElement) {
      continue;
    }

    // add the raw and channelized key
    classNames.push(key);
    if (opt_suffix) {
      classNames.push(key + "-" + opt_suffix);
    }

    if (opt_options && opt_options.isRoot && opt_suffix) {
      classNames.push(opt_suffix);
    }
  }
  return " " + classNames.join(" ");
}

PartnerBar.prototype.getSemanticType = function(key) {
  var elementType = "div";
  if (this.semanticMarkup) {
    var classMapEntry = this.classmap[key];
    if ( classMapEntry && classMapEntry.type ) {
      elementType = classMapEntry.type;
    }
  }
  return elementType;
}

/**
 * This grabs the computed styling for margins on the partner cells.
 * This is used in auto sizeing on startup.
 * Note that this code assumes universal margins for all partners in px,
 * and does not compute padded styles.
 */
PartnerBar.prototype.grabComputedPartnerMargins = function() {
  var mlStyle = (this.ie ? 'marginLeft' : 'margin-left');
  var mrStyle = (this.ie ? 'marginRight' : 'margin-right');
  // Dom element to use to grab computed style.
  var dom = this.partners[0].root;
  var ml;
  var mr;
  if (dom.currentStyle) {
    ml = dom.currentStyle[mlStyle];
    mr = dom.currentStyle[mrStyle];
  } else if (document.defaultView && document.defaultView.getComputedStyle) {
    var domCS = document.defaultView.getComputedStyle(dom, null);
    ml = domCS.getPropertyValue(mlStyle);
    mr = domCS.getPropertyValue(mrStyle);
  }
  var mlpx = parseInt(ml) || 0;
  var mrpx = parseInt(mr) || 0;
  return {'ml' : mlpx, 'mr' : mrpx};
}

})();
