const seedProducts = [
 {id:"p1",name:"UltraTech PPC Cement 50 kg",category:"Cement",unit:"bag",emoji:"🧱",subtitle:"Premium blended cement",offers:[
  {seller:"Shree Traders",status:"APPROVED",price:390,stock:500,moq:10,active:true},
  {seller:"BuildMart Supplies",status:"APPROVED",price:385,stock:240,moq:5,active:true},
  {seller:"Om Construction Depot",status:"APPROVED",price:405,stock:90,moq:20,active:true},
  {seller:"Pending Seller Co.",status:"PENDING",price:375,stock:600,moq:10,active:true}]},
 {id:"p2",name:"TMT Steel Bars 12 mm",category:"Steel",unit:"piece",emoji:"🏗️",subtitle:"Fe 500D grade · 12 metre",offers:[
  {seller:"Shree Traders",status:"APPROVED",price:685,stock:120,moq:10,active:true},
  {seller:"BuildMart Supplies",status:"APPROVED",price:672,stock:0,moq:5,active:true},
  {seller:"Om Construction Depot",status:"APPROVED",price:699,stock:250,moq:20,active:true}]},
 {id:"p3",name:"Red Clay Bricks",category:"Bricks & Blocks",unit:"piece",emoji:"🧱",subtitle:"First-class solid bricks",offers:[
  {seller:"Shree Traders",status:"APPROVED",price:9,stock:3000,moq:500,active:true},
  {seller:"Om Construction Depot",status:"APPROVED",price:8.5,stock:1800,moq:1000,active:true}]},
 {id:"p4",name:"River Sand",category:"Sand & Aggregate",unit:"ton",emoji:"⛰️",subtitle:"Washed natural river sand",offers:[
  {seller:"BuildMart Supplies",status:"APPROVED",price:1450,stock:45,moq:2,active:true},
  {seller:"Om Construction Depot",status:"APPROVED",price:1520,stock:20,moq:1,active:true}]},
 {id:"p5",name:"PVC Plumbing Pipe 4 inch",category:"Plumbing",unit:"piece",emoji:"🚰",subtitle:"Heavy-duty PVC · 3 metre",offers:[
  {seller:"Shree Traders",status:"APPROVED",price:285,stock:80,moq:5,active:true},
  {seller:"BuildMart Supplies",status:"APPROVED",price:279,stock:150,moq:10,active:true}]},
 {id:"p6",name:"Modular Switch 6A",category:"Electrical",unit:"piece",emoji:"🔌",subtitle:"ISI-marked electrical fitting",offers:[
  {seller:"Shree Traders",status:"APPROVED",price:65,stock:240,moq:10,active:true}]},
 {id:"p7",name:"Interior Emulsion Paint 20L",category:"Paint",unit:"bucket",emoji:"🪣",subtitle:"Smooth matte finish",offers:[
  {seller:"BuildMart Supplies",status:"APPROVED",price:2350,stock:32,moq:1,active:true},
  {seller:"Om Construction Depot",status:"APPROVED",price:2299,stock:18,moq:2,active:true}]},
 {id:"p8",name:"Concrete Aggregate 20 mm",category:"Sand & Aggregate",unit:"ton",emoji:"🪨",subtitle:"Crushed stone aggregate",offers:[
  {seller:"Shree Traders",status:"APPROVED",price:1180,stock:0,moq:2,active:true},
  {seller:"BuildMart Supplies",status:"APPROVED",price:1210,stock:60,moq:3,active:true}]}
];
const sellers = ["Shree Traders","BuildMart Supplies","Om Construction Depot","Pending Seller Co."];
let products = JSON.parse(localStorage.getItem("bajrixProducts") || "null") || seedProducts;
let currentCategory = "all";
let openOffers = new Set();
let toastTimer;
const $ = id => document.getElementById(id);
const rupees = n => "₹" + Number(n).toLocaleString("en-IN",{maximumFractionDigits:2});
const escapeHtml = s => String(s).replace(/[&<>"']/g,c=>({"&":"&amp;","<":"&lt;",">":"&gt;",'"':"&quot;","'":"&#39;"}[c]));
function persist(){localStorage.setItem("bajrixProducts",JSON.stringify(products))}
function approvedOffers(product){return product.offers.filter(o=>o.status==="APPROVED"&&o.active&&o.stock>0)}
function minOffer(product){return approvedOffers(product).sort((a,b)=>a.price-b.price)[0]}
function categoryClass(category){return ({"Cement":"cement","Steel":"steel","Bricks & Blocks":"bricks","Sand & Aggregate":"sand","Plumbing":"plumbing","Electrical":"electrical","Paint":"paint"})[category]||"cement"}
function renderProducts(){
 const q=$("searchInput").value.trim().toLowerCase(), cat=$("categoryFilter").value, sort=$("sortFilter").value;
 let list=products.filter(p=>(cat==="all"||p.category===cat)&&(currentCategory==="all"||p.category===currentCategory)&&`${p.name} ${p.category} ${p.subtitle}`.toLowerCase().includes(q)&&approvedOffers(p).length);
 if(sort==="low")list.sort((a,b)=>minOffer(a).price-minOffer(b).price);if(sort==="high")list.sort((a,b)=>minOffer(b).price-minOffer(a).price);if(sort==="name")list.sort((a,b)=>a.name.localeCompare(b.name));
 $("productGrid").innerHTML=list.map(p=>{const best=minOffer(p), offers=approvedOffers(p).sort((a,b)=>a.price-b.price), expanded=openOffers.has(p.id);
  return `<article class="product-card"><div class="product-visual ${categoryClass(p.category)}"><span class="product-label">${escapeHtml(p.category.toUpperCase())}</span><span class="visual-emoji">${p.emoji||"📦"}</span></div><div class="product-body"><div class="product-cat">${escapeHtml(p.category)}</div><h3>${escapeHtml(p.name)}</h3><div class="product-sub">${escapeHtml(p.subtitle||"Quality construction material")}</div><div class="best-price"><strong>${rupees(best.price)}</strong><span>/ ${escapeHtml(p.unit)}</span></div><div class="offer-row"><div class="seller-info"><strong><span class="verified">✓</span> ${escapeHtml(best.seller)}</strong><small>${offers.length} seller offer${offers.length===1?"":"s"} · Min. ${best.moq} ${escapeHtml(p.unit)}</small></div><button class="offer-btn" data-offers="${p.id}">${expanded?"Hide offers":"Compare offers"} ↗</button></div></div><div class="offer-details ${expanded?"open":""}">${offers.map(o=>`<div class="offer-line"><div><strong>${escapeHtml(o.seller)}</strong><br><span>${o.stock<20?`Only ${o.stock} left`:`${o.stock.toLocaleString("en-IN")} in stock`}</span></div><div><strong>${rupees(o.price)} / ${escapeHtml(p.unit)}</strong><br><span>Min. order ${o.moq}</span></div></div>`).join("")}</div></article>`}).join("");
 $("resultCount").textContent=String(list.length).padStart(2,"0");$("statProducts").textContent=products.length;$("availableCount").textContent=`${list.length} products`;
 $("emptyState").classList.toggle("hidden",list.length!==0);$("productGrid").classList.toggle("hidden",list.length===0);
}
function currentSeller(){return $("sellerSelect").value}
function sellerListings(){return products.flatMap(p=>p.offers.map((o,index)=>({...o,product:p,offerIndex:index}))).filter(x=>x.seller===currentSeller())}
function renderSeller(){
 const q=$("listingSearch").value.trim().toLowerCase(), all=sellerListings(), rows=all.filter(x=>x.product.name.toLowerCase().includes(q)||x.product.category.toLowerCase().includes(q));
 const active=all.filter(x=>x.active&&x.stock>0).length, out=all.filter(x=>x.active&&x.stock===0).length;
 $("sellerActive").textContent=active;$("sellerOut").textContent=out;
 $("sellerAvg").textContent=all.length?rupees(all.reduce((s,x)=>s+x.price,0)/all.length):"₹0";
 $("listingCount").textContent=`Showing ${rows.length} of ${all.length} listing${all.length===1?"":"s"}`;
 $("listingTable").innerHTML=rows.map(x=>`<tr><td><div class="table-product"><div class="table-emoji">${x.product.emoji||"📦"}</div><div><strong>${escapeHtml(x.product.name)}</strong><small>${escapeHtml(x.product.category)} · per ${escapeHtml(x.product.unit)}</small></div></div></td><td><strong>${rupees(x.price)}</strong></td><td>${Number(x.stock).toLocaleString("en-IN")}</td><td>${x.moq} ${escapeHtml(x.product.unit)}</td><td><span class="status-label ${x.stock===0||!x.active?"out":""}">${!x.active?"Paused":x.stock===0?"Out of stock":"Active"}</span></td><td><div class="row-actions"><button class="tiny-btn" data-edit="${x.product.id}">Edit</button><button class="tiny-btn" data-toggle="${x.product.id}">${x.active?"Pause":"Resume"}</button><button class="tiny-btn delete" data-remove="${x.product.id}" title="Remove listing">×</button></div></td></tr>`).join("");
 if(!rows.length)$("listingTable").innerHTML='<tr><td colspan="6" style="text-align:center;padding:32px;color:#929a91">No listings found.</td></tr>';
}
function showToast(message){const el=$("toast");el.textContent=message;el.classList.add("show");clearTimeout(toastTimer);toastTimer=setTimeout(()=>el.classList.remove("show"),2600)}
function setView(view){const market=view==="market";$("marketView").classList.toggle("hidden",!market);$("sellerView").classList.toggle("hidden",market);$("breadcrumbCurrent").textContent=market?"Marketplace":"Seller dashboard";document.querySelectorAll(".nav-item").forEach(b=>b.classList.toggle("active",b.dataset.view===view));if(!market)renderSeller();window.scrollTo({top:0,behavior:"smooth"})}
function resetFilters(){$("searchInput").value="";$("categoryFilter").value="all";$("sortFilter").value="popular";currentCategory="all";document.querySelectorAll(".chip").forEach(c=>c.classList.toggle("selected",c.dataset.category==="all"));renderProducts()}
function openModal(productId=null){
 const p=productId?products.find(x=>x.id===productId):null, offer=p?.offers.find(o=>o.seller===currentSeller());
 $("listingForm").reset();$("editId").value=p?.id||"";$("modalTitle").textContent=p?"Edit listing":"Add a listing";$("formName").value=p?.name||"";$("formCategory").value=p?.category||"Cement";$("formUnit").value=p?.unit||"bag";$("formPrice").value=offer?.price??"";$("formStock").value=offer?.stock??"";$("formMoq").value=offer?.moq??1;$("formError").classList.add("hidden");$("listingModal").classList.remove("hidden");$("formName").focus();
}
function closeModal(){$("listingModal").classList.add("hidden")}
function saveListing(e){
 e.preventDefault();const id=$("editId").value,name=$("formName").value.trim(),category=$("formCategory").value,unit=$("formUnit").value.trim(),price=Number($("formPrice").value),stock=Number($("formStock").value),moq=Number($("formMoq").value),seller=currentSeller();
 const error=$("formError");const fail=msg=>{error.textContent=msg;error.classList.remove("hidden")};
 if(!name||!unit)return fail("Enter a product name and unit.");if(!Number.isFinite(price)||price<=0)return fail("Price must be greater than ₹0.");if(!Number.isInteger(stock)||stock<0)return fail("Stock must be a whole number (0 or more).");if(!Number.isInteger(moq)||moq<1)return fail("Minimum order quantity must be at least 1.");
 let p=id?products.find(x=>x.id===id):products.find(x=>x.name.toLowerCase()===name.toLowerCase()&&x.category===category);
 if(!p){p={id:"p"+Date.now(),name,category,unit,emoji:({"Cement":"🧱","Steel":"🏗️","Bricks & Blocks":"🧱","Sand & Aggregate":"🪨","Plumbing":"🚰","Electrical":"🔌","Paint":"🪣"})[category]||"📦",subtitle:"Seller listed construction material",offers:[]};products.push(p)}
 else {p.name=name;p.category=category;p.unit=unit}
 let offer=p.offers.find(o=>o.seller===seller);
 if(!offer){offer={seller,status:seller==="Pending Seller Co."?"PENDING":"APPROVED",active:true,price,stock,moq};p.offers.push(offer)}
 else Object.assign(offer,{price,stock,moq,active:true});
 persist();closeModal();renderProducts();renderSeller();showToast(id?"Listing updated successfully":"Listing saved successfully");
}
function findOffer(productId){const p=products.find(x=>x.id===productId);return {p,offer:p?.offers.find(o=>o.seller===currentSeller())}}
document.querySelectorAll(".nav-item").forEach(b=>b.addEventListener("click",()=>setView(b.dataset.view)));
$("browseHelp").onclick=$("heroBrowse").onclick=()=>{setView("market");$("catalog").scrollIntoView({behavior:"smooth"})};
$("searchInput").addEventListener("input",renderProducts);$("categoryFilter").addEventListener("change",()=>{currentCategory="all";document.querySelectorAll(".chip").forEach(c=>c.classList.toggle("selected",c.dataset.category==="all"));renderProducts()});$("sortFilter").addEventListener("change",renderProducts);
$("categoryChips").addEventListener("click",e=>{const b=e.target.closest("[data-category]");if(!b)return;currentCategory=b.dataset.category;$("categoryFilter").value="all";document.querySelectorAll(".chip").forEach(c=>c.classList.toggle("selected",c===b));renderProducts()});
$("clearFilters").onclick=$("emptyReset").onclick=resetFilters;
$("productGrid").addEventListener("click",e=>{const b=e.target.closest("[data-offers]");if(!b)return;const id=b.dataset.offers;openOffers.has(id)?openOffers.delete(id):openOffers.add(id);renderProducts()});
$("sellerSelect").addEventListener("change",renderSeller);$("listingSearch").addEventListener("input",renderSeller);$("addListingBtn").onclick=()=>openModal();$("closeModal").onclick=$("cancelModal").onclick=closeModal;$("listingForm").addEventListener("submit",saveListing);
$("listingModal").addEventListener("click",e=>{if(e.target===$("listingModal"))closeModal()});
$("listingTable").addEventListener("click",e=>{
 const edit=e.target.closest("[data-edit]"),toggle=e.target.closest("[data-toggle]"),remove=e.target.closest("[data-remove]");
 if(edit){const {offer}=findOffer(edit.dataset.edit);if(offer)openModal(edit.dataset.edit);return}
 if(toggle){const {offer}=findOffer(toggle.dataset.toggle);if(offer){offer.active=!offer.active;persist();renderSeller();renderProducts();showToast(offer.active?"Listing resumed":"Listing paused")}return}
 if(remove){const {p,offer}=findOffer(remove.dataset.remove);if(!p||!offer)return;if(!confirm(`Remove ${p.name} from ${currentSeller()}'s listings?`))return;p.offers=p.offers.filter(o=>o.seller!==currentSeller());products=products.filter(x=>x.offers.length);persist();renderSeller();renderProducts();showToast("Listing removed")}
});
document.addEventListener("keydown",e=>{if(e.key==="Escape")closeModal();if((e.metaKey||e.ctrlKey)&&e.key.toLowerCase()==="k"){e.preventDefault();setView("market");$("searchInput").focus()}});
renderProducts();renderSeller();
